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

import hu.perit.ngface.sse.notification.SseUpdateNotification;
import hu.perit.spvitamin.spring.config.SpringContextMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class NotificationWatchDogTest
{
    @MockBean
    private ApplicationContext applicationContext;

    @MockBean
    SseServiceImpl sseService;

    private NotificationWatchDog notificationWatchDog;


    @BeforeEach
    public void init()
    {
        SpringContextMock.builder()
            .append(SseService.class, sseService)
            .build(applicationContext);

        notificationWatchDog = new NotificationWatchDog();
    }


    @Test
    void resendInMillisTest_shouldVerifyMock()
    {
        SseUpdateNotification updateNotification = new SseUpdateNotification("alma", new HashSet<>());

        notificationWatchDog.resendInMillis(1L, updateNotification);

        verify(sseService).resendNotification(updateNotification);
    }
}