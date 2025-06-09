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

package hu.perit.ngface.webservice.rest.controller;

import hu.perit.ngface.sse.SseService;
import hu.perit.ngface.sse.rest.NgfaceSseRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SseRestController extends NgfaceSseRestController
{
    public SseRestController(SseService sseService)
    {
        super(sseService);
    }


    //------------------------------------------------------------------------------------------------------------------
    // subscribe()
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/frontend/sse/subscribe")
    @Override
    public ResponseEntity<SseEmitter> subscribe(String lastEventId)
    {
        return super.subscribe(lastEventId);
    }
}
