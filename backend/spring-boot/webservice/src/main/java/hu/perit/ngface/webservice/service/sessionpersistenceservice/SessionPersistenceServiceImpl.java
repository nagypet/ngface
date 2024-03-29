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

package hu.perit.ngface.webservice.service.sessionpersistenceservice;

import hu.perit.ngface.webservice.service.api.SessionData;
import hu.perit.ngface.webservice.service.api.SessionPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SessionPersistenceServiceImpl implements SessionPersistenceService
{
    private final SessionCache sessionCache;


    @Override
    public void saveSessionData(SessionData sessionData)
    {
        this.sessionCache.saveSessionData(getSessionId(), sessionData);
    }


    @Override
    public SessionData getSessionData()
    {
        return this.sessionCache.getSessionData(getSessionId());
    }


    private static String getSessionId()
    {
        return ((WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getSessionId();
    }
}
