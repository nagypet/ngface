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

package hu.perit.ngface.sse;

import hu.perit.ngface.sse.notification.SseNotification;
import hu.perit.spvitamin.spring.config.SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class NotificationWatchDog
{
    @Async
    public void resendInMillis(long sleepTime, SseNotification sseNotification)
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
