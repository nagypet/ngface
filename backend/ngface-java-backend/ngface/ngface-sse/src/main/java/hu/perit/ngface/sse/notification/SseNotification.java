/*
 * Copyright (c) 2022. Innodox Technologies Zrt.
 * All rights reserved.
 */

package hu.perit.ngface.sse.notification;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the object sent to the frontend in case of an asynchronous event
 */
@Setter
@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class SseNotification
{
    public enum Type
    {
        RELOAD,
        MESSAGE,
        UPDATE
    }

    private final Type type;
}
