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

package hu.perit.ngface.sse.notification;

import hu.perit.spvitamin.core.exception.ExceptionWrapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Optional;

@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SseMessageNotification extends SseNotification
{
    public static final String TXT_ERROR = "ERROR";

    public enum Level
    {
        INFO,
        WARNING,
        ERROR
    }

    private final Level level;
    @Nullable
    private final String message;
    @Nullable
    private final String details;
    @Nullable
    private final String errorText;

    public static SseMessageNotification create(String subject, Level level, String message, String details)
    {
        return new SseMessageNotification(subject, level, message, details, null);
    }

    public static SseMessageNotification create(String subject, Throwable throwable)
    {
        if (throwable == null)
        {
            return create(subject, Level.ERROR, TXT_ERROR, null);
        }

        ExceptionWrapper exception = ExceptionWrapper.of(throwable);
        String message = Optional.ofNullable(exception.getRootCause())
                .map(t -> StringUtils.isNotBlank(t.getMessage()) ? t.getMessage() : t.toString())
                .orElse(TXT_ERROR);
        String details = throwable.getMessage();
        return create(subject, Level.ERROR, StringUtils.abbreviate(message, 50), StringUtils.abbreviate(message.equalsIgnoreCase(details) ? null : details, 200));
    }

    public SseMessageNotification(String subject, Level level, @Nullable String message, @Nullable String details, @Nullable String errorText)
    {
        super(Type.MESSAGE, subject);
        this.level = level;
        this.message = message;
        this.details = details;
        this.errorText = errorText;
    }
}
