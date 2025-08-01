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

public interface SseService
{
    /**
     * Subscribes a client to receive server-sent events (SSE).
     *
     * @param lastReceivedEventId the last received event ID sent from the client during reconnection.
     *                            If provided, the server may resend events that occurred after this ID.
     *                            A null or blank value indicates no events need to be resent.
     * @return a {@code ServerSentEvent.Subscription<String>} representing the subscription for the client,
     *         allowing the backend to send events to this client.
     */
    ServerSentEvent.Subscription<String> subscribe(String lastReceivedEventId);

    /**
     * Sends a server-sent event (SSE) notification to connected clients.
     *
     * @param sseNotification the notification to be sent. Must not be null and should contain a valid type and subject.
     */
    void sendNotification(SseNotification sseNotification);

    /**
     * Sends an error notification with the provided subject and associated throwable.
     *
     * @param subject the subject of the error notification
     * @param throwable the throwable containing details about the error
     */
    void sendError(String subject, Throwable throwable);

    /**
     * Sends a reload notification with the provided subject.
     *
     * @param subject the subject of the reload notification
     */
    void sendReload(String subject);

    // Unit test
    int getQueueSize();
}
