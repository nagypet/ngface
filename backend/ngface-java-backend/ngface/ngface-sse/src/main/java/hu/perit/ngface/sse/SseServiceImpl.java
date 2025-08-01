/*
 * Copyright 2020-2025 the original author or authors.
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

import static java.lang.Math.max;

/**
 * This service delivers notifications to the frontend. Each message is queued and served from a background thread.
 * Otherwise, we receive a communication error if the calling thread is interrupted.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SseServiceImpl implements SseService
{
    public static final String SENDING_NOTIFICATION_FORMAT = "Sending notification: {}, last sent {} ms ago";

    private final NotificationWatchDog notificationWatchDog;

    private final ServerSentEvent<SseNotification, String> serverSentEvent = new ServerSentEvent<>();
    private final LinkedBlockingDeque<SseNotification> queue = new LinkedBlockingDeque<>();
    private final Thread processingThread = new Thread(this::dispatchNotifications);
    private final Map<SseNotification, SseNotificationDispatcher> notificationDispatcherList = new HashMap<>();
    private final ApplicationEventPublisher publisher;


    @PostConstruct
    protected void postConstruct()
    {
        this.processingThread.setName("sse-notification");
        this.processingThread.start();
    }


    @PreDestroy
    protected void shutdown()
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
                // take() will block the current thread as long the queue is empty.
                SseNotification notification = this.queue.take();
                getDispatcher(notification).dispatch(notification);
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }


    // Get the right dispatcher by type and subject
    private SseNotificationDispatcher getDispatcher(SseNotification notification)
    {
        return this.notificationDispatcherList.computeIfAbsent(notification, key -> createDispatcher(key));
    }


    private SseNotificationDispatcher createDispatcher(SseNotification notification)
    {
        return switch (notification.getType())
        {
            case RELOAD ->
                    new SseReloadNotificationDispatcher(notification.getSubject(), this.notificationWatchDog, this::eventConsumer);
            case MESSAGE -> new SseMessageNotificationDispatcher(notification.getSubject(), this::eventConsumer);
            case UPDATE ->
                    new SseUpdateNotificationDispatcher(notification.getSubject(), this.notificationWatchDog, this::eventConsumer);
        };
    }


    private void eventConsumer(SseNotification notification)
    {
        this.serverSentEvent.fire(notification);
        SseNotificationFiredEvent sseNotificationFiredEvent = new SseNotificationFiredEvent(this, notification);
        this.publisher.publishEvent(sseNotificationFiredEvent);
    }


    @Override
    public ServerSentEvent.Subscription<String> subscribe(String lastReceivedEventId)
    {
        return this.serverSentEvent.subscribe(lastReceivedEventId, null);
    }


    @Override
    public synchronized void sendNotification(SseNotification sseNotification)
    {
        queueNotification(sseNotification);
    }


    @Override
    public void sendError(String subject, Throwable throwable)
    {
        SseNotification notification = SseMessageNotification.create(subject, throwable);
        queueNotification(notification);
    }


    @Override
    public void sendReload(String subject)
    {
        SseReloadNotification notification = SseReloadNotification.create(subject);
        queueNotification(notification);
    }


    // This will be called from the package internal NotificationWatchDog
    public void resendNotification(SseNotification sseNotification)
    {
        queueNotification(sseNotification);
        getDispatcher(sseNotification).triggerEventFired(sseNotification);
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
    @Override
    public int getQueueSize()
    {
        return this.queue.size();
    }


    //------------------------------------------------------------------------------------------------------------------
    // SseReloadNotificationDispatcher
    //------------------------------------------------------------------------------------------------------------------


    @RequiredArgsConstructor
    private static class SseReloadNotificationDispatcher implements SseNotificationDispatcher
    {
        private final String subject;
        private final NotificationWatchDog notificationWatchDog;
        private final Consumer<SseNotification> eventConsumer;

        private long lastReloadSent = 0;
        private SseReloadNotification pendingNotifications = null;
        private boolean triggerEventInProgress = false;


        @Override
        public boolean handlesNotification(SseNotification notification)
        {
            return notification instanceof SseReloadNotification && this.subject.equals(notification.getSubject());
        }


        @Override
        public void dispatch(SseNotification notification)
        {
            if (handlesNotification(notification))
            {
                SseReloadNotification reloadNotification = (SseReloadNotification) notification;
                // Let's see how long ago the last RELOAD event has been sent
                long diff = System.currentTimeMillis() - lastReloadSent;

                // Send the RELOAD event only if it was last sent at least 1000 ms ago
                if (diff >= 1000)
                {
                    log.info(SENDING_NOTIFICATION_FORMAT, notification, diff);
                    this.eventConsumer.accept(notification);
                    this.pendingNotifications = null;
                    lastReloadSent = System.currentTimeMillis();
                }
                else
                {
                    log.debug("Queueing notification {}, last sent {} ms ago", reloadNotification, diff);

                    // Otherwise only collect updates
                    appendToPendingNotifications(reloadNotification);

                    if (!this.triggerEventInProgress)
                    {
                        long sleepTime = max(0, 1200 - diff);
                        this.triggerEventInProgress = true;
                        notificationWatchDog.resendInMillis(sleepTime, reloadNotification);
                    }
                }
            }
        }


        private void appendToPendingNotifications(SseReloadNotification reloadNotification)
        {
            if (this.pendingNotifications == null)
            {
                this.pendingNotifications = reloadNotification;
            }
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    // SseUpdateNotificationDispatcher
    //------------------------------------------------------------------------------------------------------------------
    @RequiredArgsConstructor
    private static class SseUpdateNotificationDispatcher implements SseNotificationDispatcher
    {
        private final String subject;
        private final NotificationWatchDog notificationWatchDog;
        private final Consumer<SseNotification> eventConsumer;

        private long lastUpdateSent = 0;
        private SseUpdateNotification<?> pendingNotifications = null;
        private boolean triggerEventInProgress = false;


        @Override
        public boolean handlesNotification(SseNotification notification)
        {
            return notification instanceof SseUpdateNotification && this.subject.equals(notification.getSubject());
        }


        @Override
        public void dispatch(SseNotification notification)
        {
            if (handlesNotification(notification))
            {
                SseUpdateNotification<?> updateNotification = (SseUpdateNotification<?>) notification;
                // Let's see how long ago the last UPDATE event has been sent
                long diff = System.currentTimeMillis() - this.lastUpdateSent;

                // Send the UPDATE event only if it was last sent at least 1000 ms ago
                if (diff >= 1000)
                {
                    appendToPendingNotifications(updateNotification);
                    log.info(SENDING_NOTIFICATION_FORMAT, this.pendingNotifications, diff);
                    this.eventConsumer.accept(this.pendingNotifications);
                    this.pendingNotifications = null;
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
            if (this.pendingNotifications == null)
            {
                this.pendingNotifications = newNotification;
                return;
            }

            this.pendingNotifications.append(newNotification);
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    // SseMessageNotificationDispatcher
    //------------------------------------------------------------------------------------------------------------------
    @RequiredArgsConstructor
    private static class SseMessageNotificationDispatcher implements SseNotificationDispatcher
    {
        private final String subject;
        private final Consumer<SseNotification> eventConsumer;


        @Override
        public boolean handlesNotification(SseNotification notification)
        {
            return notification instanceof SseMessageNotification && this.subject.equals(notification.getSubject());
        }


        @Override
        public void dispatch(SseNotification notification)
        {
            if (handlesNotification(notification))
            {
                // Sending messages immediatelly
                log.info(SENDING_NOTIFICATION_FORMAT, notification, "-");
                this.eventConsumer.accept(notification);
            }
        }
    }

}
