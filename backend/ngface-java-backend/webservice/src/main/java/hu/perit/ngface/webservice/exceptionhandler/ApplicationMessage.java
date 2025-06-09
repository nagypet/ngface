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

package hu.perit.ngface.webservice.exceptionhandler;

import hu.perit.spvitamin.core.exception.AbstractMessageType;
import hu.perit.spvitamin.core.exception.LogLevel;
import hu.perit.spvitamin.spring.config.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public enum ApplicationMessage implements AbstractMessageType
{
    CONSTRAINT_VIOLATION_1                          (400, LogLevel.DEBUG),
    ONLY_EVEN_IDS_CAN_BE_EDITED                     (400, LogLevel.DEBUG),
    CANNOT_DELETE                                   (400, LogLevel.DEBUG)
    ;

    private final int httpStatusCode;
    private final LogLevel level;
    private Object[] params;

    ApplicationMessage(int statusCode, LogLevel level)
    {
        this.httpStatusCode = statusCode;
        this.level = level;
    }

    @Override
    public ApplicationMessage params(Object... params)
    {
        this.params = params;

        // Putting String type parameters onto single quotes
        if (this.params != null)
        {
            for (int i = 0; i < this.params.length; i++)
            {
                if (this.params[i] instanceof String && !((String) this.params[i]).startsWith("'"))
                {
                    this.params[i] = "'" + this.params[i] + "'";
                }
            }
        }

        return this;
    }


    @Override
    public int getHttpStatusCode()
    {
        return httpStatusCode;
    }


    @Override
    public LogLevel getLevel()
    {
        return level;
    }


    @Override
    public String getMessage()
    {
        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        return messageSource.getMessage(this.name(), params, Locale.getDefault());
    }
}
