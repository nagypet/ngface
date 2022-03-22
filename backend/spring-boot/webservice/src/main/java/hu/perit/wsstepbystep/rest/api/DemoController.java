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
import hu.perit.ngface.data.SubmitFormData;
import hu.perit.ngface.widget.form.Form;
import hu.perit.wsstepbystep.ngface.democomponent.DemoComponentController;
import hu.perit.wsstepbystep.ngface.democomponent.DemoComponentData;
import hu.perit.wsstepbystep.ngface.democomponent.DemoComponentView;
import hu.perit.wsstepbystep.ngface.tabledetailscomponent.TableDetailsComponentController;
import hu.perit.wsstepbystep.ngface.tabledetailscomponent.TableDetailsComponentData;
import hu.perit.wsstepbystep.ngface.tabledetailscomponent.TableDetailsComponentView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DemoController implements DemoApi
{
    private final ComponentController<DemoComponentController.Params, DemoComponentData> demoComponentController;
    private final ComponentController<TableDetailsComponentController.Params, TableDetailsComponentData> tableDetailsComponentController;


    //------------------------------------------------------------------------------------------------------------------
    // getDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public Form getDemoForm(Long pageNumber, Long pageSize, String sortColumn, String sortDirection)
    {
        DemoComponentController.Params params = new DemoComponentController.Params(pageNumber, pageSize, sortColumn, sortDirection);
        log.debug("getDemoForm({})", params);

        DemoComponentData data = this.demoComponentController.initializeData(params);
        return new DemoComponentView(data).getForm();
    }


    //------------------------------------------------------------------------------------------------------------------
    // submitDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void submitDemoForm(SubmitFormData submitFormData)
    {
        log.debug("submitDemoForm({})", submitFormData);

        DemoComponentData data = new DemoComponentData();
        data.formSubmitted(submitFormData);
        this.demoComponentController.onSave(data);
    }


    //------------------------------------------------------------------------------------------------------------------
    // getTableDetailsForm()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public Form getTableDetailsForm(Long id)
    {
        TableDetailsComponentController.Params params = new TableDetailsComponentController.Params(id);
        log.debug("getTableDetailsForm({})", params);

        TableDetailsComponentData data = this.tableDetailsComponentController.initializeData(params);
        return new TableDetailsComponentView(data).getForm();
    }


    //------------------------------------------------------------------------------------------------------------------
    // submitTableDetailsForm()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void submitTableDetailsForm(SubmitFormData submitFormData)
    {
        log.debug("submitTableDetailsForm({})", submitFormData);

        TableDetailsComponentData data = new TableDetailsComponentData();
        data.formSubmitted(submitFormData);
        this.tableDetailsComponentController.onSave(data);
    }

}
