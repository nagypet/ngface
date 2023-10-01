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

import hu.perit.ngface.core.data.DataRetrievalParams;
import hu.perit.ngface.core.data.SubmitFormData;
import hu.perit.ngface.core.widget.form.Form;
import hu.perit.ngface.core.widget.table.Filterer;
import hu.perit.ngface.webservice.config.Constants;
import hu.perit.ngface.webservice.ngface.democomponent.DemoComponentController;
import hu.perit.ngface.webservice.ngface.democomponent.DemoComponentDTO;
import hu.perit.ngface.webservice.ngface.democomponent.DemoComponentView;
import hu.perit.ngface.webservice.ngface.tabledetailscomponent.TableDetailsComponentController;
import hu.perit.ngface.webservice.ngface.tabledetailscomponent.TableDetailsComponentDTO;
import hu.perit.ngface.webservice.ngface.tabledetailscomponent.TableDetailsComponentView;
import hu.perit.ngface.webservice.rest.api.DemoApi;
import hu.perit.spvitamin.spring.restmethodlogger.LoggedRestMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DemoController implements DemoApi
{
    private final DemoComponentController demoComponentController;
    private final TableDetailsComponentController tableDetailsComponentController;


    //------------------------------------------------------------------------------------------------------------------
    // getDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    @LoggedRestMethod(eventId = Constants.DEMO_API_GET_DEMO_FORM, subsystem = Constants.SUBSYSTEM_NAME)
    public Form getDemoForm(DataRetrievalParams dataRetrievalParams)
    {
        DemoComponentController.Params params = new DemoComponentController.Params(dataRetrievalParams, null);

        DemoComponentDTO data = this.demoComponentController.initializeData(params);
        return new DemoComponentView(data).getForm();
    }


    //------------------------------------------------------------------------------------------------------------------
    // getDemoFormTableRow()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    @LoggedRestMethod(eventId = Constants.DEMO_API_GET_DEMO_FORM_TABLE_ROW, subsystem = Constants.SUBSYSTEM_NAME)
    public Form getDemoFormTableRow(String rowId)
    {
        DemoComponentController.Params params = new DemoComponentController.Params(null, Long.parseLong(rowId));

        DemoComponentDTO data = this.demoComponentController.initializeData(params);
        return new DemoComponentView(data).getForm();
    }


    //------------------------------------------------------------------------------------------------------------------
    // getColumnFilterer()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    @LoggedRestMethod(eventId = Constants.DEMO_API_GET_DEMO_FORM_COLUMN_FILTERER, subsystem = Constants.SUBSYSTEM_NAME)
    public Filterer getColumnFilterer(String column, String searchText)
    {
        return this.demoComponentController.getFilterer(column, searchText);
    }


    //------------------------------------------------------------------------------------------------------------------
    // submitDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    @LoggedRestMethod(eventId = Constants.DEMO_API_SUBMIT_DEMO_FORM, subsystem = Constants.SUBSYSTEM_NAME)
    public void submitDemoForm(SubmitFormData submitFormData)
    {
        log.debug("submitDemoForm({})", submitFormData);

        DemoComponentDTO data = new DemoComponentDTO();
        data.formSubmitted(submitFormData);
        this.demoComponentController.onSave(data);
    }


    //------------------------------------------------------------------------------------------------------------------
    // getTableDetailsForm()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    @LoggedRestMethod(eventId = Constants.DEMO_API_GET_TABLE_DETAILS_FORM, subsystem = Constants.SUBSYSTEM_NAME)
    public Form getTableDetailsForm(Long id)
    {
        TableDetailsComponentController.Params params = new TableDetailsComponentController.Params(id);
        log.debug("getTableDetailsForm({})", params);

        TableDetailsComponentDTO data = this.tableDetailsComponentController.initializeData(params);
        return new TableDetailsComponentView(data).getForm();
    }


    //------------------------------------------------------------------------------------------------------------------
    // submitTableDetailsForm()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    @LoggedRestMethod(eventId = Constants.DEMO_API_SUBMIT_TABLE_DETAILS_FORM, subsystem = Constants.SUBSYSTEM_NAME)
    public void submitTableDetailsForm(SubmitFormData submitFormData)
    {
        log.debug("submitTableDetailsForm({})", submitFormData);

        TableDetailsComponentDTO data = new TableDetailsComponentDTO();
        data.formSubmitted(submitFormData);
        this.tableDetailsComponentController.onSave(data);
    }

}
