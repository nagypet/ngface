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

import hu.perit.ngface.controller.ComponentController;
import hu.perit.ngface.data.DataRetrievalParams;
import hu.perit.ngface.data.SubmitFormData;
import hu.perit.ngface.widget.form.Form;
import hu.perit.spvitamin.spring.restmethodlogger.LoggedRestMethod;
import hu.perit.wsstepbystep.config.Constants;
import hu.perit.wsstepbystep.ngface.democomponent.DemoComponentController;
import hu.perit.wsstepbystep.ngface.democomponent.DemoComponentDTO;
import hu.perit.wsstepbystep.ngface.democomponent.DemoComponentView;
import hu.perit.wsstepbystep.ngface.tabledetailscomponent.TableDetailsComponentController;
import hu.perit.wsstepbystep.ngface.tabledetailscomponent.TableDetailsComponentDTO;
import hu.perit.wsstepbystep.ngface.tabledetailscomponent.TableDetailsComponentView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DemoController implements DemoApi
{
    private final ComponentController<DemoComponentController.Params, DemoComponentDTO> demoComponentController;
    private final ComponentController<TableDetailsComponentController.Params, TableDetailsComponentDTO> tableDetailsComponentController;


    //------------------------------------------------------------------------------------------------------------------
    // getDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    @LoggedRestMethod(eventId = 1, subsystem = Constants.SUBSYSTEM_NAME)
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
    @LoggedRestMethod(eventId = 1, subsystem = Constants.SUBSYSTEM_NAME)
    public Form getDemoFormTableRow(String rowId)
    {
        DemoComponentController.Params params = new DemoComponentController.Params(null, rowId);

        DemoComponentDTO data = this.demoComponentController.initializeData(params);
        return new DemoComponentView(data).getForm();
    }



    //------------------------------------------------------------------------------------------------------------------
    // submitDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    @LoggedRestMethod(eventId = 2, subsystem = Constants.SUBSYSTEM_NAME)
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
    @LoggedRestMethod(eventId = 3, subsystem = Constants.SUBSYSTEM_NAME)
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
    @LoggedRestMethod(eventId = 4, subsystem = Constants.SUBSYSTEM_NAME)
    public void submitTableDetailsForm(SubmitFormData submitFormData)
    {
        log.debug("submitTableDetailsForm({})", submitFormData);

        TableDetailsComponentDTO data = new TableDetailsComponentDTO();
        data.formSubmitted(submitFormData);
        this.tableDetailsComponentController.onSave(data);
    }

}
