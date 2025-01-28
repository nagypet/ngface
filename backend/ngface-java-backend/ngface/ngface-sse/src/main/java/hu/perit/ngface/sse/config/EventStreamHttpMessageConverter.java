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

package hu.perit.ngface.sse.config;

import hu.perit.spvitamin.spring.exceptionhandler.RestExceptionResponse;
import hu.perit.spvitamin.spring.logging.ObjectLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

@Slf4j
public class EventStreamHttpMessageConverter extends AbstractHttpMessageConverter<RestExceptionResponse>
{
    public EventStreamHttpMessageConverter()
    {
        super(new MediaType("text", "event-stream"));
    }


    @Override
    protected boolean supports(Class<?> clazz)
    {
        return clazz.isAssignableFrom(RestExceptionResponse.class);
    }


    @Override
    protected RestExceptionResponse readInternal(Class<? extends RestExceptionResponse> clazz, HttpInputMessage inputMessage)
        throws HttpMessageNotReadableException
    {
        return null;
    }


    @Override
    protected void writeInternal(RestExceptionResponse restExceptionResponse, HttpOutputMessage outputMessage)
        throws HttpMessageNotWritableException
    {
        log.info("writeInternal() {}", ObjectLogger.toString(restExceptionResponse));
    }
}
