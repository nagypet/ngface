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

package hu.perit.ngface.rest;

import hu.perit.ngface.core.controller.ComponentController;
import hu.perit.ngface.core.data.ComponentDTO;
import hu.perit.ngface.core.types.intf.SubmitFormData;
import hu.perit.ngface.core.view.ComponentView;
import hu.perit.ngface.core.widget.form.Form;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class NgfaceFormRestController<C extends ComponentController<D, I>, D extends ComponentDTO, V extends ComponentView, I>
    implements NgfaceFormRestApi<I>
{
    protected final C componentController;


    @Override
    public Form getForm(I id)
    {
        D data = this.componentController.getForm(id);
        return supplyView(data).getForm();
    }


    @Override
    public void submitForm(SubmitFormData submitFormData)
    {
        D data = supplyDTO();
        data.formSubmitted(submitFormData);
        this.componentController.onFormSubmit(data);
    }


    protected abstract V supplyView(D data);

    protected abstract D supplyDTO();
}
