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

import hu.perit.spvitamin.json.JSonSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SseUpdateNotificationTest
{
    public static final String SUBJECT = "alma";

    private SseUpdateNotification<Long> sseUpdateNotification;


    @BeforeEach
    void init()
    {
        sseUpdateNotification = new SseUpdateNotification<>(SUBJECT, Set.of(1L));
    }


    @Test
    void getterTest_shouldReturnCorrectValues()
    {
        assertThat(sseUpdateNotification.getType()).isEqualTo(SseNotification.Type.UPDATE);
    }


    @Test
    void equalsTest_whenComparedToItself_shouldReturnTrue()
    {
        assertThat(sseUpdateNotification).isEqualTo(sseUpdateNotification);
    }


    @Test
    void equalsTest_whenComparedToNull_shouldReturnFalse()
    {
        assertThat(sseUpdateNotification).isNotEqualTo(null);
    }


    @Test
    void equalsTest_whenComparedToOtherWithSameTypeAndSameFields_shouldReturnTrue()
    {
        SseUpdateNotification<Long> other = new SseUpdateNotification<>(SUBJECT, new HashSet<>());
        other.getJobIds().add(1L);

        assertThat(sseUpdateNotification).isEqualTo(other);
    }


    @Test
    void equalsTest_whenComparedToOtherWithSameTypeAndDifferentFields_shouldReturnFalse()
    {
        SseUpdateNotification other = new SseUpdateNotification(SUBJECT, new HashSet<>());

        assertThat(sseUpdateNotification).isNotEqualTo(other);
    }


    @Test
    void equalsTest_whenComparedToOtherWithDifferentType_shouldReturnFalse()
    {
        assertThat(sseUpdateNotification).isNotEqualTo(String.valueOf(1));
    }


    @Test
    void canEqualTest_shouldReturnTrue()
    {
        assertThat(sseUpdateNotification.canEqual(sseUpdateNotification)).isTrue();
    }


    @Test
    void hashCodeTest_shouldReturnInteger()
    {
        SseUpdateNotification other = new SseUpdateNotification(SUBJECT, new HashSet<>());
        other.getJobIds().add(1L);

        assertThat(sseUpdateNotification.hashCode()).isEqualTo(other.hashCode());
    }


    @Test
    void appendTest_shouldRun()
    {
        SseUpdateNotification<Long> toAppend = new SseUpdateNotification<>(SUBJECT, new HashSet<>());
        toAppend.getJobIds().add(2L);

        sseUpdateNotification.append(toAppend);

        assertThat(sseUpdateNotification.getJobIds().contains(1L)).isTrue();
        assertThat(sseUpdateNotification.getJobIds().contains(2L)).isTrue();
    }




    @Test
    void sseUpdateNotification()
    {
        SseUpdateNotification<?> expected = new SseUpdateNotification<>(SUBJECT, new HashSet<>());
        String json = JSonSerializer.toJson(expected);

        SseNotification deserialized = JSonSerializer.fromJson(json, SseNotification.class);
        assertThat(deserialized).isInstanceOf(SseUpdateNotification.class);
        assertThat(deserialized).isEqualTo(expected);
    }
}
