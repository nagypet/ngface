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
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class SseReloadNotificationTest
{
    private static final String SUBJECT = "subject";

    private final SseReloadNotification sseReloadNotification = new SseReloadNotification(SUBJECT);


    @Test
    void getterTest_shouldReturnCorrectValues()
    {
        assertThat(sseReloadNotification.getType()).isEqualTo(SseNotification.Type.RELOAD);
    }


    @Test
    void toStringTest_shouldReturnString()
    {
        assertThat(sseReloadNotification.toString()).isEqualTo("SseReloadNotification()");
    }


    @Test
    void equalsTest_whenComparedToItself_shouldReturnTrue()
    {
        assertThat(sseReloadNotification).isEqualTo(sseReloadNotification);
    }


    @Test
    void equalsTest_whenComparedToNull_shouldReturnFalse()
    {
        assertThat(sseReloadNotification).isNotEqualTo(null);
    }


    @Test
    void equalsTest_whenComparedToOtherWithSameTypeAndSameFields_shouldReturnTrue()
    {
        SseReloadNotification other = new SseReloadNotification(SUBJECT);

        assertThat(sseReloadNotification).isEqualTo(other);
    }


    @Test
    void equalsTest_whenComparedToOtherWithDifferentType_shouldReturnFalse()
    {
        assertThat(sseReloadNotification).isNotEqualTo(String.valueOf(1));
    }


    @Test
    void canEqualTest_shouldReturnTrue()
    {
        assertThat(sseReloadNotification.canEqual(sseReloadNotification)).isTrue();
    }


    @Test
    void hashCodeTest_shouldReturnInteger()
    {
        SseReloadNotification other = new SseReloadNotification(SUBJECT);

        assertThat(sseReloadNotification.hashCode()).isEqualTo(other.hashCode());
    }


    @Test
    void SseReloadNotificationTest() throws IOException
    {
        SseReloadNotification expected = new SseReloadNotification(SUBJECT);
        String json = JSonSerializer.toJson(expected);

        SseNotification deserialized = JSonSerializer.fromJson(json, SseNotification.class);
        assertThat(deserialized).isInstanceOf(SseReloadNotification.class);
        assertThat(deserialized).isEqualTo(expected);
    }
}
