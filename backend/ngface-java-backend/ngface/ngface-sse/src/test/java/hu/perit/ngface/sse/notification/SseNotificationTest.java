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

package hu.perit.ngface.sse.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SseNotificationTest
{
    private static final String SUBJECT = "subject";

    private final SseNotification sseNotification = new SseNotification(SseNotification.Type.RELOAD, SUBJECT);


    @Test
    void getterTest_shouldReturnCorrectValues()
    {
        assertEquals(SseNotification.Type.RELOAD, sseNotification.getType());
    }


    @Test
    void toStringTest_shouldReturnString()
    {
        assertEquals(
                "SseNotification(type=RELOAD, subject=subject)",
                sseNotification.toString());
    }


    @Test
    void equalsTest_whenComparedToItself_shouldReturnTrue()
    {
        assertEquals(sseNotification, sseNotification);
    }


    @Test
    void equalsTest_whenComparedToNull_shouldReturnFalse()
    {
        assertNotEquals(null, sseNotification);
    }


    @Test
    void equalsTest_whenComparedToOtherWithSameTypeAndSameFields_shouldReturnTrue()
    {
        SseNotification other = new SseNotification(SseNotification.Type.RELOAD, SUBJECT);

        assertEquals(sseNotification, other);
    }


    @Test
    void equalsTest_whenComparedToOtherWithSameTypeAndDifferentFields_shouldReturnFalse()
    {
        SseNotification other = new SseNotification(SseNotification.Type.MESSAGE, SUBJECT);

        assertNotEquals(sseNotification, other);
    }


    @Test
    void equalsTest_whenComparedToOtherWithDifferentType_shouldReturnFalse()
    {
        assertNotEquals(sseNotification, String.valueOf(1));
    }


    @Test
    void canEqualTest_shouldReturnTrue()
    {
        assertTrue(sseNotification.canEqual(sseNotification));
    }


    @Test
    void hashCodeTest_shouldReturnInteger()
    {
        SseNotification other = new SseNotification(SseNotification.Type.RELOAD, SUBJECT);

        assertEquals(sseNotification.hashCode(), other.hashCode());
    }
}
