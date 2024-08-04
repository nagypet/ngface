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

import hu.perit.spvitamin.core.StackTracer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;

/**
 * Backend -> Frontend communication. The frontend may call the subscribe() method via a REST interface in order to get
 * an instance of an SseEvent (Server-sent-event) object. Calling the fire() method from within the backend will initiate
 * the sending of an event to each frontend which are subscribed.
 *
 * @param <T>
 *     Type of event to be sent to the client
 * @param <F>
 *     Type of filter key of subscriptions. This can be used to reduce network traffic in order only subscribers
 *     matching a certain criteria will receive the event.
 * @author np
 */
@Slf4j
public class ServerSentEvent<T, F>
{

    public enum ResendPolicy
    {
        ONLY_LAST_EVENT,
        ALL_EVENTS
    }

    private final ResendPolicy resendPolicy;
    private final AtomicInteger lastSubscriptionId = new AtomicInteger(0);
    private final Map<Integer, Subscription<F>> subscribers = new HashMap<>();
    private final AtomicInteger lastEventId = new AtomicInteger(0);
    private final CircularFifoQueue<EventQueueItem<T>> eventQueue;


    public ServerSentEvent()
    {
        this.resendPolicy = ResendPolicy.ONLY_LAST_EVENT;
        this.eventQueue = new CircularFifoQueue<>(1);
    }


    public ServerSentEvent(ResendPolicy resendPolicy, int maxEventQueueSize)
    {
        this.resendPolicy = resendPolicy;
        this.eventQueue = new CircularFifoQueue<>(maxEventQueueSize);
    }


    /**
     * @param lastReceivedEventIdAsString
     *     When the SSE connection was closed, the browser sends a new subscribe request, and in this request, he sends
     *     the last received message id. Upon receiving a non null lastReceivedEventIdAsString, the server should resend the last event.
     * @param filterKey
     *     This key can be used to filter subscriptions in the fire() method.
     * @return
     */
    public synchronized Subscription<F> subscribe(String lastReceivedEventIdAsString, F filterKey)
    {
        Subscription<F> subscription = new Subscription<>(this.lastSubscriptionId.incrementAndGet(), 30_000L, filterKey);
        synchronized (this)
        {
            this.subscribers.put(subscription.id, subscription);
            log.debug("Subscription added: {}. Total count of subscriptions: {}", subscription.id, this.subscribers.size());

            if (StringUtils.isNotBlank(lastReceivedEventIdAsString))
            {
                int lastReceivedId = Integer.parseInt(lastReceivedEventIdAsString);
                // Újraküldjük a queue-ból az újabb elemeket
                for (EventQueueItem<T> event : this.eventQueue)
                {
                    if (event.eventId > lastReceivedId)
                    {
                        this.sendEvent(subscription, event.eventId, event.getEvent());
                    }
                }
            }
            else
            {
                // Ilyenkor küldünk egy szinkronizáló eseményt az aktuális esemény id-val, hogy a frontend ezt el tudja
                // tárolni, és a következő újracsatlakozásnál már ezt adja vissza.
                this.sendSyncEvent(subscription, this.lastEventId.get());
            }
        }

        subscription.onCompletion(() -> {
            log.debug("Subscription completed: {}", subscription.id);
            this.remove(subscription.id);
        });
        subscription.onTimeout(() -> {
            log.debug("Subscription timed out: {}", subscription.id);
            subscription.complete();
            this.remove(subscription.id);
        });

        return subscription;
    }


    synchronized void remove(int id)
    {
        if (this.subscribers.containsKey(id))
        {
            this.subscribers.remove(id);
            log.debug("Subscription removed: {}. Total count of subscriptions: {}", id, this.subscribers.size());
        }
    }


    /**
     * Default media type is Json. This function should not throw any exception.
     *
     * @param args
     */
    public synchronized void fire(T args)
    {
        // Készítünk egy másolatot, és azon iterálunk
        List<Subscription<F>> subscriptions = new ArrayList<>(this.subscribers.values());
        int eventId = this.lastEventId.incrementAndGet();
        this.eventQueue.add(new EventQueueItem<>(eventId, args));
        for (Subscription<F> subscription : subscriptions)
        {
            this.sendEvent(subscription, eventId, args);
        }
    }


    /**
     * Default media type is Json. This function should not throw any exception.
     *
     * @param args
     */
    public synchronized void fire(T args, F filterCriteria, BiPredicate<F, F> filterFunction)
    {
        // Készítünk egy másolatot, és azon iterálunk
        List<Subscription<F>> subscriptions = new ArrayList<>(this.subscribers.values());
        int eventId = this.lastEventId.incrementAndGet();
        this.eventQueue.add(new EventQueueItem<>(eventId, args));
        for (Subscription<F> subscription : subscriptions)
        {
            if (filterFunction.test(filterCriteria, subscription.filterKey))
            {
                this.sendEvent(subscription, eventId, args);
            }
        }
    }


    /**
     * This event type sends the last eventId to the frontend
     *
     * @param subscription
     * @param eventId
     */
    private void sendSyncEvent(Subscription<F> subscription, int eventId)
    {
        this.sendEvent("synchronizing", subscription, eventId, null);
    }


    private void sendEvent(Subscription<F> subscription, int eventId, T args)
    {
        this.sendEvent("message", subscription, eventId, args);
    }


    private void sendEvent(String eventName, Subscription<F> subscription, int eventId, T args)
    {
        try
        {
            log.debug("Sending event {}:{} => subscription {}", eventName, eventId, subscription.id);
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                .data(args == null ? "NULL" : args)
                .name(eventName)
                .id(String.valueOf(eventId));
            // Ha SseEventBuilder-t adunk át, akkor nem lehet MediaType-ot definiálni
            subscription.send(event);
            //subscription.send(args, MediaType.APPLICATION_JSON);
        }
        catch (Exception ex)
        {
            log.info("Subscription failed: {}", subscription.id);
            // completeWithError has been commented out with spring boot 3.3.2 because it just caused problems.
            //subscription.completeWithError(ex);
            if (ex instanceof IOException)
            {
                log.info(ex.toString());
            }
            else
            {
                log.error(StackTracer.toString(ex));
            }
            this.remove(subscription.id);
        }
    }


    @Getter
    public static class Subscription<F> extends SseEmitter
    {
        private final int id;
        private final F filterKey;


        public Subscription(int id, Long timeout, F filterKey)
        {
            super(timeout);
            this.id = id;
            this.filterKey = filterKey;
        }


        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }

            if (o == null || getClass() != o.getClass())
            {
                return false;
            }

            Subscription<?> that = (Subscription<?>) o;

            return new EqualsBuilder()
                .append(id, that.id)
                .isEquals();
        }


        @Override
        public int hashCode()
        {
            return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
        }
    }


    @RequiredArgsConstructor
    @Getter
    static class EventQueueItem<T>
    {
        private final int eventId;
        private final T event;
    }
}
