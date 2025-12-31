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
import hu.perit.ngface.sse.notification.SseUpdateNotification;
import hu.perit.spvitamin.core.took.Took;
import hu.perit.spvitamin.test.spring.config.SpringContextMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Slf4j
class NotificationWatchDogTest
{
    @MockitoBean
    private ApplicationContext applicationContext;

    @MockitoBean
    SseServiceImpl sseService;

    private NotificationWatchDog notificationWatchDog;


    @BeforeEach
    void init()
    {
        SpringContextMock.builder()
                .append(SseService.class, sseService)
                .build(applicationContext);

        notificationWatchDog = new NotificationWatchDog();
    }


    @Test
    void resendInMillisTest_shouldVerifyMock()
    {
        SseUpdateNotification<Long> updateNotification = new SseUpdateNotification<>("alma", new HashSet<>());

        notificationWatchDog.resendInMillis(200L, updateNotification);

        try (Took took = new Took())
        {
            ArgumentCaptor<SseNotification> argumentCaptor = ArgumentCaptor.forClass(SseNotification.class);
            log.debug("Waiting for verify() for 200 ms ...");
            await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> verify(sseService).resendNotification(argumentCaptor.capture()));

            SseNotification sseNotification = argumentCaptor.getValue();
            assertThat(took.getDuration()).isGreaterThan(200L);
            assertThat(sseNotification).isNotNull();
            assertThat(sseNotification.getSubject()).isEqualTo("alma");
        }
    }
}
