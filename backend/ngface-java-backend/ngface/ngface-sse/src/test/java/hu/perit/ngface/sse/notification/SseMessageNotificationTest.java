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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SseMessageNotificationTest
{
    SseMessageNotification sseMessageNotification;

    private static final SseMessageNotification.Level LEVEL = SseMessageNotification.Level.INFO;
    private static final String SUBJECT = "subject";
    private static final String MESSAGE = "message";
    private static final String DETAILS = "details";
    private static final String ERROR_TEXT = "errorText";


    @BeforeEach
    public void init()
    {
        sseMessageNotification = new SseMessageNotification(SUBJECT, LEVEL, MESSAGE, DETAILS, ERROR_TEXT);
    }


    @Test
    void getterTest_shouldReturnCorrectValues()
    {
        assertEquals(LEVEL, sseMessageNotification.getLevel());
        assertEquals(MESSAGE, sseMessageNotification.getMessage());
        assertEquals(DETAILS, sseMessageNotification.getDetails());
        assertEquals(ERROR_TEXT, sseMessageNotification.getErrorText());
    }


    @Test
    void equalsTest_whenComparedToItself_shouldReturnTrue()
    {
        assertEquals(sseMessageNotification, sseMessageNotification);
    }


    @Test
    void equalsTest_whenComparedToNull_shouldReturnFalse()
    {
        assertNotEquals(null, sseMessageNotification);
    }


    @Test
    void equalsTest_whenComparedToOtherWithSameTypeAndSameFields_shouldReturnTrue()
    {
        SseMessageNotification other = new SseMessageNotification(SUBJECT, LEVEL, MESSAGE, DETAILS, ERROR_TEXT);

        assertEquals(sseMessageNotification, other);
    }


    @Test
    void equalsTest_whenComparedToOtherWithSameTypeAndDifferentFields_shouldReturnFalse()
    {
        SseMessageNotification other = new SseMessageNotification(SUBJECT, LEVEL, "", DETAILS, ERROR_TEXT);

        assertNotEquals(sseMessageNotification, other);
    }


    @Test
    void equalsTest_whenComparedToOtherWithDifferentType_shouldReturnFalse()
    {
        assertNotEquals(sseMessageNotification, String.valueOf(1));
    }


    @Test
    void canEqualTest_shouldReturnTrue()
    {
        assertTrue(sseMessageNotification.canEqual(sseMessageNotification));
    }


    @Test
    void hashCodeTest_shouldReturnInteger()
    {
        SseMessageNotification other = new SseMessageNotification(SUBJECT, LEVEL, MESSAGE, DETAILS, ERROR_TEXT);

        assertEquals(sseMessageNotification.hashCode(), other.hashCode());
    }


    @Test
    void toStringTest_shouldReturnString()
    {
        assertEquals("SseMessageNotification(level=INFO, message=message, details=details," +
                        " errorText=errorText)",
                sseMessageNotification.toString());
    }
}
