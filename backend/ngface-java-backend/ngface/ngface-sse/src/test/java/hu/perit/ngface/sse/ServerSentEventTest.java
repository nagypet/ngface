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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ServerSentEventTest
{
    private ServerSentEvent serverSentEvent;
    private int id = 1;
    private long timeout = 30000L;
    private Long filterKey = 1L;
    private ServerSentEvent.Subscription<Long> subscription;


    @BeforeEach
    void init()
    {
        serverSentEvent = new ServerSentEvent<String, Long>();
        serverSentEvent = new ServerSentEvent(ServerSentEvent.ResendPolicy.ONLY_LAST_EVENT, 1);

        subscription = new ServerSentEvent.Subscription(id, timeout, filterKey);
    }


    @Test
    void fire1Test_shouldRun()
    {
        subscription = serverSentEvent.subscribe("1", (Long) null);
        serverSentEvent.fire(new ArrayList<>());
        assertNotNull(subscription);
    }


    @Test
    void fire2Test_shouldRun()
    {
        subscription = serverSentEvent.subscribe("1", 1);
        serverSentEvent.fire(new ArrayList<>(), 1, this::filterFunction);
        assertNotNull(subscription);
    }


    @Test
    void subscribeTest_shouldRun()
    {
        subscription = serverSentEvent.subscribe("1", null);
        assertNotNull(subscription);
    }


    @Test
    void subscribeTest_whenLastReceivedEventIdAsStringIsEmpty_shouldRun()
    {
        subscription = serverSentEvent.subscribe("", null);
        assertNotNull(subscription);
    }


    @Test
    void removeTest_shouldRun()
    {
        subscription = serverSentEvent.subscribe("1", null);
        serverSentEvent.remove(1);
        assertNotNull(subscription);
    }


    @Test
    void eventQueueItemGetterTest_shouldReturnCorrectValues()
    {
        int eventId = 1;
        int event = 1;
        ServerSentEvent.EventQueueItem eventQueueItem = new ServerSentEvent.EventQueueItem(eventId, event);

        assertEquals(eventId, eventQueueItem.getEventId());
        assertEquals(event, eventQueueItem.getEvent());
    }


    @Test
    void subscriptionGetterTest_shouldReturnCorrectValues()
    {
        assertEquals(id, subscription.getId());
        assertEquals(filterKey, subscription.getFilterKey());
    }


    @Test
    void subscriptionEqualsTest_whenComparedToItself_shouldReturnTrue()
    {
        assertEquals(subscription, subscription);
    }


    @Test
    void subscriptionEqualsTest_whenComparedToNull_shouldReturnFalse()
    {
        assertNotEquals(null, subscription);
    }


    @Test
    void subscriptionEqualsTest_whenComparedToOtherWithSameTypeAndSameFields_shouldReturnTrue()
    {
        ServerSentEvent.Subscription other = new ServerSentEvent.Subscription(id, timeout, filterKey);

        assertEquals(subscription, other);
    }


    @Test
    void subscriptionEqualsTest_whenComparedToOtherWithSameTypeAndDifferentFields_shouldReturnFalse()
    {
        ServerSentEvent.Subscription other = new ServerSentEvent.Subscription(2, 100L, 5);

        assertNotEquals(subscription, other);
    }


    @Test
    void subscriptionEqualsTest_whenComparedToOtherWithDifferentType_shouldReturnFalse()
    {
        assertNotEquals(subscription, String.valueOf(1));
    }


    @Test
    void subscriptionHashCodeTest_shouldReturnInteger()
    {
        ServerSentEvent.Subscription other = new ServerSentEvent.Subscription(id, timeout, filterKey);

        assertEquals(subscription.hashCode(), other.hashCode());
    }


    private boolean filterFunction(Object o, Object o1)
    {
        return true;
    }
}
