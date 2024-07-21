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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SseUpdateNotification extends SseNotification
{
    private final String subject;
    private final Set<Long> jobIds;

    public static SseUpdateNotification create(String subject, Long jobId)
    {
        return new SseUpdateNotification(subject, Set.of(jobId));
    }

    public SseUpdateNotification(String subject, Set<Long> jobIds)
    {
        super(Type.UPDATE);
        this.subject = subject;
        this.jobIds = new HashSet<>(jobIds);
    }

    public void append(SseUpdateNotification updateNotification)
    {
        this.jobIds.addAll(updateNotification.jobIds);
    }

}
