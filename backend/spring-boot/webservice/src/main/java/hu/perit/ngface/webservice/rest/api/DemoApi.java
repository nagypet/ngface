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

package hu.perit.ngface.webservice.rest.api;

import hu.perit.ngface.core.data.DataRetrievalParams;
import hu.perit.ngface.core.data.SubmitFormData;
import hu.perit.ngface.core.widget.form.Form;
import hu.perit.ngface.core.widget.table.Filterer;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


public interface DemoApi
{
    String BASE_URL_DEMO = "/frontend/demo";
    String BASE_URL_TABLE_DETAILS = "/frontend/table-details";
    String URL_GET = "/get";
    String URL_SUBMIT = "/submit";
    String URL_COLVALUESET = "/colvalueset";

    //------------------------------------------------------------------------------------------------------------------
    // getDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @PostMapping(BASE_URL_DEMO + URL_GET)
    Form getDemoForm(@Valid @RequestBody DataRetrievalParams dataRetrievalParams);


    //------------------------------------------------------------------------------------------------------------------
    // getDemoFormTableRow()
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(BASE_URL_DEMO + URL_GET)
    Form getDemoFormTableRow(@RequestParam String rowId);


    //------------------------------------------------------------------------------------------------------------------
    // getColumnFilterer()
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(BASE_URL_DEMO + URL_COLVALUESET)
    Filterer getColumnFilterer(
        @RequestHeader(value = "column") String column,
        @RequestHeader(value = "searchText") String searchText
    );


    //------------------------------------------------------------------------------------------------------------------
    // submitDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @PostMapping(BASE_URL_DEMO + URL_SUBMIT)
    void submitDemoForm(@RequestBody SubmitFormData submitFormData);


    //------------------------------------------------------------------------------------------------------------------
    // getTableDetailsForm()
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(BASE_URL_TABLE_DETAILS)
    Form getTableDetailsForm(@RequestParam Long id
    );


    //------------------------------------------------------------------------------------------------------------------
    // submitTableDetailsForm()
    //------------------------------------------------------------------------------------------------------------------
    @PostMapping(BASE_URL_TABLE_DETAILS)
    void submitTableDetailsForm(@RequestBody SubmitFormData submitFormData);
}
