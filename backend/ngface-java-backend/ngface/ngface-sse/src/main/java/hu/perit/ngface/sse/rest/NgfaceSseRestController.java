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

package hu.perit.ngface.sse.rest;

import hu.perit.ngface.sse.ServerSentEvent;
import hu.perit.ngface.sse.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
public class NgfaceSseRestController implements NgfaceSseRestApi
{
    private final SseService sseService;

    //------------------------------------------------------------------------------------------------------------------
    // subscribe()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<SseEmitter> subscribe(String lastEventId)
    {
        ServerSentEvent.Subscription<String> subscription = this.sseService.subscribe(lastEventId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_EVENT_STREAM);
        headers.add("X-Accel-Buffering", "no");
        return new ResponseEntity<>(subscription, headers, HttpStatus.OK);
    }
}
