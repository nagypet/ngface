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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SseUpdateNotificationTest
{
    public static final String SUBJECT = "alma";

    private SseUpdateNotification sseUpdateNotification;


    @BeforeEach
    public void init()
    {
        sseUpdateNotification = new SseUpdateNotification(SUBJECT, Set.of(1L));
    }


    @Test
    void getterTest_shouldReturnCorrectValues()
    {
        assertEquals(SseNotification.Type.UPDATE, sseUpdateNotification.getType());
    }


    @Test
    void toStringTest_shouldReturnString()
    {
        assertEquals(
            "SseUpdateNotification(super=SseNotification(type=UPDATE, subject=alma), jobIds=[1])",
            sseUpdateNotification.toString());
    }


    @Test
    void equalsTest_whenComparedToItself_shouldReturnTrue()
    {
        assertEquals(sseUpdateNotification, sseUpdateNotification);
    }


    @Test
    void equalsTest_whenComparedToNull_shouldReturnFalse()
    {
        assertNotEquals(null, sseUpdateNotification);
    }


    @Test
    void equalsTest_whenComparedToOtherWithSameTypeAndSameFields_shouldReturnTrue()
    {
        SseUpdateNotification other = new SseUpdateNotification(SUBJECT, new HashSet<>());
        other.getJobIds().add(1L);

        assertEquals(sseUpdateNotification, other);
    }


    @Test
    void equalsTest_whenComparedToOtherWithSameTypeAndDifferentFields_shouldReturnFalse()
    {
        SseUpdateNotification other = new SseUpdateNotification(SUBJECT, new HashSet<>());

        assertNotEquals(sseUpdateNotification, other);
    }


    @Test
    void equalsTest_whenComparedToOtherWithDifferentType_shouldReturnFalse()
    {
        assertNotEquals(sseUpdateNotification, String.valueOf(1));
    }


    @Test
    void canEqualTest_shouldReturnTrue()
    {
        assertTrue(sseUpdateNotification.canEqual(sseUpdateNotification));
    }


    @Test
    void hashCodeTest_shouldReturnInteger()
    {
        SseUpdateNotification other = new SseUpdateNotification(SUBJECT, new HashSet<>());
        other.getJobIds().add(1L);

        assertEquals(sseUpdateNotification.hashCode(), other.hashCode());
    }


    @Test
    void appendTest_shouldRun()
    {
        SseUpdateNotification toAppend = new SseUpdateNotification(SUBJECT, new HashSet<>());
        toAppend.getJobIds().add(2L);

        sseUpdateNotification.append(toAppend);

        assertTrue(sseUpdateNotification.getJobIds().contains(1L));
        assertTrue(sseUpdateNotification.getJobIds().contains(2L));
    }
}
