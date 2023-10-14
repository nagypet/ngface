/*
 * Copyright 2020-2023 the original author or authors.
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

import hu.perit.ngface.webservice.config.Constants;
import hu.perit.ngface.webservice.rest.api.AddressRestApi;
import hu.perit.ngface.webservice.service.api.AddressService;
import hu.perit.spvitamin.spring.restmethodlogger.LoggedRestMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddressRestController implements AddressRestApi
{
    private final AddressService addressService;


    @Override
    @LoggedRestMethod(eventId = Constants.ADDRESS_API_SEARCH, subsystem = Constants.SUBSYSTEM_NAME)
    public void loadFromFile() throws Exception
    {
        this.addressService.deleteAll();
        this.addressService.loadFromFile("addresses_budapest.csv", "Budapest");
        this.addressService.loadFromFile("addresses_miskolc.csv", "Miskolc");
    }
}
