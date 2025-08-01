/*
 * Copyright (c) 2025. Innodox Technologies Zrt.
 * All rights reserved.
 */

package hu.perit.ngface.sse;

import hu.perit.ngface.sse.notification.SseNotification;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

@Getter
@ToString
public class SseNotificationFiredEvent extends ApplicationEvent
{
    @Serial
    private static final long serialVersionUID = 7729790818058402771L;

    private final SseNotification sseNotification;

    public SseNotificationFiredEvent(Object source, SseNotification sseNotification)
    {
        super(source);
        this.sseNotification = sseNotification;
    }
}
