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

package hu.perit.ngface.sse.notification;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.text.MessageFormat;

/**
 * This is the object sent to the frontend in case of an asynchronous event
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SseReloadNotification.class, name = "RELOAD"),
        @JsonSubTypes.Type(value = SseMessageNotification.class, name = "MESSAGE"),
        @JsonSubTypes.Type(value = SseUpdateNotification.class, name = "UPDATE")
})
public abstract class SseNotification implements java.io.Serializable
{
    @Serial
    private static final long serialVersionUID = 340568346417904190L;


    public enum Type
    {
        RELOAD,
        MESSAGE,
        UPDATE
    }


    private final Type type;
    private final String subject;
    // This is the originator of the notification.
    private String sender;


    protected SseNotification(Type type, String subject)
    {
        if (StringUtils.isBlank(subject))
        {
            throw new IllegalStateException(MessageFormat.format("The subject is mandatory for {0}", this.getClass().getName()));
        }

        if (type == null)
        {
            throw new IllegalStateException(MessageFormat.format("The type is mandatory for {0}", this.getClass().getName()));
        }

        this.subject = subject;
        this.type = type;
    }
}
