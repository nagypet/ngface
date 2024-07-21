/*
 * Copyright 2020-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.perit.ngface.sse;

import hu.perit.ngface.sse.notification.SseMessageNotification;
import hu.perit.ngface.sse.notification.SseNotification;
import hu.perit.ngface.sse.notification.SseReloadNotification;
import hu.perit.ngface.sse.notification.SseUpdateNotification;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import static java.lang.Math.max;

/**
 * This service delivers notifications to the frontend. Each message is queued and served from a background thread.
 * Otherwise, we receive a communication error if the calling thread is interrupted.
 */
@Service
@Slf4j
public class SseServiceImpl implements SseService
{
    public static final String SENDING_NOTIFICATION_FORMAT = "Sending notification: {}, last sent {} ms ago";

    private final ServerSentEvent<SseNotification, String> serverSentEvent;
    private final LinkedBlockingDeque<SseNotification> queue = new LinkedBlockingDeque<>();
    private final Thread processingThread = new Thread(this::dispatchNotifications);
    private final NotificationWatchDog notificationWatchDog;
    private final List<SseNotificationDispatcher> notificationDispatcherList = new ArrayList<>(List.of(
            new SseReloadNotificationDispatcher(),
            new SseMessageNotificationDispatcher()
    ));

    @Autowired
    public SseServiceImpl(NotificationWatchDog notificationWatchDog)
    {
        this(notificationWatchDog, new ServerSentEvent<>());
    }

    public SseServiceImpl(NotificationWatchDog notificationWatchDog, ServerSentEvent<SseNotification, String> serverSentEvent)
    {
        this.notificationWatchDog = notificationWatchDog;
        this.serverSentEvent = serverSentEvent;
    }

    @PostConstruct
    protected void postConstruct()
    {
        this.processingThread.setName("sse-notification");
        this.processingThread.start();
    }

    @PreDestroy
    protected void shotdown()
    {
        this.processingThread.interrupt();
    }


    /**
     * This function delivers frontend notifications, but has some intelligence. Look at the following log. As we can see there are
     * sometimes RELOAD messages just with a couple of ms time difference. This causes the browser to flicker, we can just drop
     * some of the RELOAD events if they are too close to each other. I will try to make a small delay and ignore subsequent RELOAD events
     * if they are within this period.
     * 2022-06-12 12:08:27.109 INFO  --- [se-notification] .u.s.i.f.FrontendNotificationServiceImpl  52 : Sending notification: SseNotification(type=RELOAD, level=INFO, message=null, details=null, errorText=null)
     * 2022-06-12 12:08:27.125 INFO  --- [se-notification] .u.s.i.f.FrontendNotificationServiceImpl  52 : Sending notification: SseNotification(type=RELOAD, level=INFO, message=null, details=null, errorText=null)
     * 2022-06-12 12:08:28.469 INFO  --- [se-notification] .u.s.i.f.FrontendNotificationServiceImpl  52 : Sending notification: SseNotification(type=RELOAD, level=INFO, message=null, details=null, errorText=null)
     * 2022-06-12 12:08:28.483 INFO  --- [se-notification] .u.s.i.f.FrontendNotificationServiceImpl  52 : Sending notification: SseNotification(type=RELOAD, level=INFO, message=null, details=null, errorText=null)
     * 2022-06-12 12:08:29.528 INFO  --- [se-notification] .u.s.i.f.FrontendNotificationServiceImpl  52 : Sending notification: SseNotification(type=RELOAD, level=INFO, message=null, details=null, errorText=null)
     * 2022-06-12 12:08:29.552 INFO  --- [se-notification] .u.s.i.f.FrontendNotificationServiceImpl  52 : Sending notification: SseNotification(type=RELOAD, level=INFO, message=null, details=null, errorText=null)
     * 2022-06-12 12:08:30.646 INFO  --- [se-notification] .u.s.i.f.FrontendNotificationServiceImpl  52 : Sending notification: SseNotification(type=RELOAD, level=INFO, message=null, details=null, errorText=null)
     * 2022-06-12 12:08:31.759 INFO  --- [se-notification] .u.s.i.f.FrontendNotificationServiceImpl  52 : Sending notification: SseNotification(type=RELOAD, level=INFO, message=null, details=null, errorText=null)
     * 2022-06-12 12:08:33.132 INFO  --- [se-notification] .u.s.i.f.FrontendNotificationServiceImpl  52 : Sending notification: SseNotification(type=RELOAD, level=INFO, message=null, details=null, errorText=null)
     * 2022-06-12 12:08:34.830 INFO  --- [se-notification] .u.s.i.f.FrontendNotificationServiceImpl  52 : Sending notification: SseNotification(type=RELOAD, level=INFO, message=null, details=null, errorText=null)
     * 2022-06-12 12:08:36.218 INFO  --- [se-notification] .u.s.i.f.FrontendNotificationServiceImpl  52 : Sending notification: SseNotification(type=RELOAD, level=INFO, message=null, details=null, errorText=null)
     * 2022-06-12 12:08:37.141 INFO  --- [se-notification] .u.s.i.f.FrontendNotificationServiceImpl  52 : Sending notification: SseNotification(type=RELOAD, level=INFO, message=null, details=null, errorText=null)
     */
    protected void dispatchNotifications()
    {
        while (!Thread.currentThread().isInterrupted())
        {
            try
            {
                // take() will block the current thread as long the queue is empty. We want to optimize
                SseNotification notification = this.queue.take();
                this.notificationDispatcherList.forEach(d -> d.dispatch(notification));
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }


    @Override
    public ServerSentEvent.Subscription<String> subscribe(String lastReceivedEventId)
    {
        return this.serverSentEvent.subscribe(lastReceivedEventId, null);
    }

    @Override
    public synchronized void sendNotification(SseNotification sseNotification)
    {
        if (sseNotification instanceof SseUpdateNotification updateNotification)
        {
            if (StringUtils.isBlank(updateNotification.getSubject()))
            {
                throw new IllegalStateException(MessageFormat.format("The subject is mandatory for {0}", SseUpdateNotification.class.getName()));
            }
            if (!dispatcherExists(updateNotification.getSubject()))
            {
                this.notificationDispatcherList.add(new SseUpdateNotificationDispatcher(updateNotification.getSubject()));
            }
        }
        queueNotification(sseNotification);
    }

    private boolean dispatcherExists(String subject)
    {
        return this.notificationDispatcherList.stream()
                .filter(SseUpdateNotificationDispatcher.class::isInstance)
                .anyMatch(u -> ((SseUpdateNotificationDispatcher) u).getSubject().equals(subject));
    }


    @Override
    public void sendError(Throwable throwable)
    {
        SseNotification notification = SseMessageNotification.create(throwable);
        queueNotification(notification);
    }

    @Override
    public void sendReload()
    {
        SseReloadNotification notification = SseReloadNotification.create();
        queueNotification(notification);
    }


    protected void resendNotification(SseNotification sseNotification)
    {
        queueNotification(sseNotification);
        this.notificationDispatcherList.forEach(d -> d.triggerEventFired(sseNotification));
    }


    private void queueNotification(SseNotification sseNotification)
    {
        try
        {
            this.queue.put(sseNotification);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }


    // Unit test
    int getQueueSize()
    {
        return this.queue.size();
    }

    //------------------------------------------------------------------------------------------------------------------
    // SseReloadNotificationDispatcher
    //------------------------------------------------------------------------------------------------------------------

    private class SseReloadNotificationDispatcher implements SseNotificationDispatcher
    {
        private long lastReloadSent = 0;

        @Override
        public boolean handlesNotification(SseNotification notification)
        {
            return notification instanceof SseReloadNotification;
        }

        @Override
        public void dispatch(SseNotification notification)
        {
            if (handlesNotification(notification))
            {
                // Let's see how long ago the last RELOAD event has been sent
                long diff = System.currentTimeMillis() - lastReloadSent;

                // Send the RELOAD event only if it was last sent at least 1000 ms ago
                if (diff >= 1000)
                {
                    log.info(SENDING_NOTIFICATION_FORMAT, notification, diff);
                    serverSentEvent.fire(notification);
                    lastReloadSent = System.currentTimeMillis();
                }

                // Otherwise skip sending
            }
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    // SseUpdateNotificationDispatcher
    //------------------------------------------------------------------------------------------------------------------
    @RequiredArgsConstructor
    private class SseUpdateNotificationDispatcher implements SseNotificationDispatcher
    {
        private long lastUpdateSent = 0;
        private SseUpdateNotification pendingUpdates = null;
        private boolean triggerEventInProgress = false;
        @Getter
        private final String subject;

        @Override
        public boolean handlesNotification(SseNotification notification)
        {
            return notification instanceof SseUpdateNotification updateNotification && this.subject.equals(updateNotification.getSubject());
        }

        @Override
        public void dispatch(SseNotification notification)
        {
            if (handlesNotification(notification))
            {
                SseUpdateNotification updateNotification = (SseUpdateNotification) notification;
                // Let's see how long ago the last UPDATE event has been sent
                long diff = System.currentTimeMillis() - this.lastUpdateSent;

                // Send the UPDATE event only if it was last sent at least 1000 ms ago
                if (diff >= 1000)
                {
                    appendToPendingNotifications(updateNotification);
                    log.info(SENDING_NOTIFICATION_FORMAT, this.pendingUpdates, diff);
                    serverSentEvent.fire(this.pendingUpdates);
                    this.pendingUpdates = null;
                    this.lastUpdateSent = System.currentTimeMillis();
                }
                else
                {
                    log.debug("Queueing notification {}, last sent {} ms ago", updateNotification, diff);

                    // Otherwise only collect updates
                    appendToPendingNotifications(updateNotification);

                    if (!this.triggerEventInProgress)
                    {
                        long sleepTime = max(0, 1200 - diff);
                        this.triggerEventInProgress = true;
                        notificationWatchDog.resendInMillis(sleepTime, updateNotification);
                    }
                }
            }
        }

        @Override
        public void triggerEventFired(SseNotification notification)
        {
            if (handlesNotification(notification))
            {
                this.triggerEventInProgress = false;
            }
        }

        private void appendToPendingNotifications(SseUpdateNotification newNotification)
        {
            if (this.pendingUpdates == null)
            {
                this.pendingUpdates = newNotification;
                return;
            }

            this.pendingUpdates.append(newNotification);
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    // SseMessageNotificationDispatcher
    //------------------------------------------------------------------------------------------------------------------
    private class SseMessageNotificationDispatcher implements SseNotificationDispatcher
    {
        @Override
        public boolean handlesNotification(SseNotification notification)
        {
            return notification instanceof SseMessageNotification;
        }

        @Override
        public void dispatch(SseNotification notification)
        {
            if (handlesNotification(notification))
            {
                // Sending messages immediatelly
                log.info(SENDING_NOTIFICATION_FORMAT, notification, "-");
                serverSentEvent.fire(notification);
            }
        }
    }

}
