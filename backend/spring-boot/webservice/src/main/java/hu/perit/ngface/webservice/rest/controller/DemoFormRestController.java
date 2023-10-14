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

import hu.perit.ngface.core.types.intf.SubmitFormData;
import hu.perit.ngface.core.widget.form.Form;
import hu.perit.ngface.rest.NgfaceFormRestController;
import hu.perit.ngface.webservice.config.Constants;
import hu.perit.ngface.webservice.ngface.democomponent.DemoComponentController;
import hu.perit.ngface.webservice.ngface.democomponent.DemoComponentDTO;
import hu.perit.ngface.webservice.ngface.democomponent.DemoComponentView;
import hu.perit.spvitamin.spring.restmethodlogger.LoggedRestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontend/forms/demo")
public class DemoFormRestController extends NgfaceFormRestController<DemoComponentController, DemoComponentDTO, DemoComponentView, Long>
{
    public DemoFormRestController(DemoComponentController componentController)
    {
        super(componentController);
    }


    @Override
    @LoggedRestMethod(eventId = Constants.DEMO_FORM_CONTROLLER_GET_FORM, subsystem = Constants.SUBSYSTEM_NAME)
    public Form getForm(Long ignored)
    {
        return super.getForm(ignored);
    }


    @Override
    @LoggedRestMethod(eventId = Constants.DEMO_FORM_CONTROLLER_SUBMIT_FORM, subsystem = Constants.SUBSYSTEM_NAME)
    public void submitForm(SubmitFormData submitFormData)
    {
        super.submitForm(submitFormData);
    }


    @Override
    protected DemoComponentView supplyView(DemoComponentDTO data)
    {
        return new DemoComponentView(data);
    }


    @Override
    protected DemoComponentDTO supplyDTO()
    {
        return new DemoComponentDTO();
    }
}
