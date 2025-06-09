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

package hu.perit.ngface.webservice.scheduler;

import hu.perit.ngface.sse.SseService;
import hu.perit.ngface.sse.notification.SseUpdateNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TaskRunner
{
    private final SseService sseService;


    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 1)
    void sendNotification()
    {
        this.sseService.sendNotification(SseUpdateNotification.create("tick", 0L));
    }
}
