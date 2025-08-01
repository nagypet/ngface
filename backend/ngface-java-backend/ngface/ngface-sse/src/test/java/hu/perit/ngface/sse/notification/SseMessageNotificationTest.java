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

import hu.perit.spvitamin.spring.json.JSonSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class SseMessageNotificationTest
{
    SseMessageNotification sseMessageNotification;

    private static final SseMessageNotification.Level LEVEL = SseMessageNotification.Level.INFO;
    private static final String SUBJECT = "subject";
    private static final String MESSAGE = "message";
    private static final String DETAILS = "details";
    private static final String ERROR_TEXT = "errorText";


    @BeforeEach
    void init()
    {
        sseMessageNotification = new SseMessageNotification(SUBJECT, LEVEL, MESSAGE, DETAILS, ERROR_TEXT);
    }


    @Test
    void getterTest_shouldReturnCorrectValues()
    {
        assertThat(sseMessageNotification.getLevel()).isEqualTo(LEVEL);
        assertThat(sseMessageNotification.getMessage()).isEqualTo(MESSAGE);
        assertThat(sseMessageNotification.getDetails()).isEqualTo(DETAILS);
        assertThat(sseMessageNotification.getErrorText()).isEqualTo(ERROR_TEXT);
    }


    @Test
    void equalsTest_whenComparedToItself_shouldReturnTrue()
    {
        assertThat(sseMessageNotification).isEqualTo(sseMessageNotification);
    }


    @Test
    void equalsTest_whenComparedToNull_shouldReturnFalse()
    {
        assertThat(sseMessageNotification).isNotEqualTo(null);
    }


    @Test
    void equalsTest_whenComparedToOtherWithSameTypeAndSameFields_shouldReturnTrue()
    {
        SseMessageNotification other = new SseMessageNotification(SUBJECT, LEVEL, MESSAGE, DETAILS, ERROR_TEXT);

        assertThat(sseMessageNotification).isEqualTo(other);
    }


    @Test
    void equalsTest_whenComparedToOtherWithSameTypeAndDifferentFields_shouldReturnFalse()
    {
        SseMessageNotification other = new SseMessageNotification(SUBJECT, LEVEL, "", DETAILS, ERROR_TEXT);

        assertThat(sseMessageNotification).isNotEqualTo(other);
    }


    @Test
    void equalsTest_whenComparedToOtherWithDifferentType_shouldReturnFalse()
    {
        assertThat(sseMessageNotification).isNotEqualTo(String.valueOf(1));
    }


    @Test
    void canEqualTest_shouldReturnTrue()
    {
        assertThat(sseMessageNotification.canEqual(sseMessageNotification)).isTrue();
    }


    @Test
    void hashCodeTest_shouldReturnInteger()
    {
        SseMessageNotification other = new SseMessageNotification(SUBJECT, LEVEL, MESSAGE, DETAILS, ERROR_TEXT);

        assertThat(sseMessageNotification).hasSameHashCodeAs(other);
    }


    @Test
    void sseMessageNoticationTest() throws IOException
    {
        SseMessageNotification expected = new SseMessageNotification(SUBJECT, LEVEL, MESSAGE, DETAILS, ERROR_TEXT);
        String json = JSonSerializer.toJson(expected);

        SseNotification deserialized = JSonSerializer.fromJson(json, SseNotification.class);
        assertThat(deserialized).isInstanceOf(SseMessageNotification.class).isEqualTo(expected);
    }
}
