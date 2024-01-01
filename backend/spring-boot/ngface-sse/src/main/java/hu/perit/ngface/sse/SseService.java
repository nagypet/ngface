/*
 * Copyright (c) 2022. Innodox Technologies Zrt.
 * All rights reserved.
 */

package hu.perit.ngface.sse;


import hu.perit.ngface.sse.notification.SseNotification;

public interface SseService
{
    ServerSentEvent.Subscription<String> subscribe(String lastReceivedEventId);

    void sendNotification(SseNotification sseNotification);

    /**
     *
     * @param throwable
     */
    void sendError(Throwable throwable);

    /**
     * Sends a reload message to the frontend
     */
    void sendReload();
}
