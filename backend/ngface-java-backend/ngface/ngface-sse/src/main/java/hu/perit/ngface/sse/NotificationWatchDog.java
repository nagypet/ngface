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

package hu.perit.ngface.sse;

import hu.perit.ngface.sse.notification.SseNotification;
import hu.perit.spvitamin.spring.config.SpringContext;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class NotificationWatchDog
{
    private final ExecutorService executor;

    public NotificationWatchDog()
    {
        BasicThreadFactory factory = BasicThreadFactory.builder()
                .namingPattern("sse-watchdog-%d")
                .build();
        executor = Executors.newFixedThreadPool(100, factory);
    }


    @PreDestroy
    private void shutdown()
    {
        this.executor.shutdown();
    }


    // The @Async annotation did not work in every case
    public void resendInMillis(long sleepTime, SseNotification sseNotification)
    {
        this.executor.submit(new AsyncJob(sleepTime, sseNotification));
    }


    @RequiredArgsConstructor
    private static class AsyncJob implements Runnable
    {
        private final Long sleepTime;
        private final SseNotification sseNotification;


        @Override
        public void run()
        {
            // To avoid circular dependencies
            SseServiceImpl sseService = (SseServiceImpl) SpringContext.getBean(SseService.class);
            try
            {
                if (sleepTime > 0)
                {
                    TimeUnit.MILLISECONDS.sleep(sleepTime);
                }
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
            log.debug("Fireing trigger event {}", sseNotification);
            sseService.resendNotification(sseNotification);
        }
    }
}
