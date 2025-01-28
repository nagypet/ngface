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

package hu.perit.ngface.sse.notification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SseReloadNotificationTest
{
    private static final String SUBJECT = "subject";

    private final SseReloadNotification sseReloadNotification = new SseReloadNotification(SUBJECT);


    @Test
    void getterTest_shouldReturnCorrectValues()
    {
        assertEquals(SseNotification.Type.RELOAD, sseReloadNotification.getType());
    }


    @Test
    void toStringTest_shouldReturnString()
    {
        assertEquals(
                "SseReloadNotification()",
                sseReloadNotification.toString());
    }


    @Test
    void equalsTest_whenComparedToItself_shouldReturnTrue()
    {
        assertEquals(sseReloadNotification, sseReloadNotification);
    }


    @Test
    void equalsTest_whenComparedToNull_shouldReturnFalse()
    {
        assertNotEquals(null, sseReloadNotification);
    }


    @Test
    void equalsTest_whenComparedToOtherWithSameTypeAndSameFields_shouldReturnTrue()
    {
        SseReloadNotification other = new SseReloadNotification(SUBJECT);

        assertEquals(sseReloadNotification, other);
    }


    @Test
    void equalsTest_whenComparedToOtherWithDifferentType_shouldReturnFalse()
    {
        assertNotEquals(sseReloadNotification, String.valueOf(1));
    }


    @Test
    void canEqualTest_shouldReturnTrue()
    {
        assertTrue(sseReloadNotification.canEqual(sseReloadNotification));
    }


    @Test
    void hashCodeTest_shouldReturnInteger()
    {
        SseReloadNotification other = new SseReloadNotification(SUBJECT);

        assertEquals(sseReloadNotification.hashCode(), other.hashCode());
    }
}
