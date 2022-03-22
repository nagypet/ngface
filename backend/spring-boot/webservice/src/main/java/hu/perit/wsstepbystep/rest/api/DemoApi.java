/*
 * Copyright 2020-2022 the original author or authors.
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

package hu.perit.wsstepbystep.rest.api;

import hu.perit.ngface.data.SubmitFormData;
import hu.perit.ngface.widget.form.Form;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface DemoApi
{
    String BASE_URL_DEMO = "/demo";
    String BASE_URL_TABLE_DETAILS = "/table-details";

    //------------------------------------------------------------------------------------------------------------------
    // getDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(BASE_URL_DEMO)
    Form getDemoForm(
            @RequestParam(required = false) Long pageNumber,
            @RequestParam(required = false) Long pageSize,
            @RequestParam(required = false) String sortColumn,
            @RequestParam(required = false) String sortDirection
    );


    //------------------------------------------------------------------------------------------------------------------
    // submitDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @PostMapping(BASE_URL_DEMO)
    void submitDemoForm(@RequestBody SubmitFormData submitFormData);


    //------------------------------------------------------------------------------------------------------------------
    // getTableDetailsForm()
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(BASE_URL_TABLE_DETAILS)
    Form getTableDetailsForm(
            @RequestParam Long id
    );


    //------------------------------------------------------------------------------------------------------------------
    // submitTableDetailsForm()
    //------------------------------------------------------------------------------------------------------------------
    @PostMapping(BASE_URL_TABLE_DETAILS)
    void submitTableDetailsForm(@RequestBody SubmitFormData submitFormData);
}
