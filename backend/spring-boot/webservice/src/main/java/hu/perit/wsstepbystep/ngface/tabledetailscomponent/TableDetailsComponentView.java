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

package hu.perit.wsstepbystep.ngface.tabledetailscomponent;

import hu.perit.ngface.view.ComponentView;
import hu.perit.ngface.widget.button.Button;
import hu.perit.ngface.widget.form.Form;
import hu.perit.ngface.widget.input.NumericInput;
import hu.perit.ngface.widget.input.TextInput;
import hu.perit.ngface.widget.input.validator.Required;
import hu.perit.wsstepbystep.config.Constants;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TableDetailsComponentView implements ComponentView
{
    private final TableDetailsComponentData data;

    @Override
    public Form getForm()
    {
        return new Form(data.getId())
                .title(String.format("Details of %s", this.data.getName()))
                .addWidget(new TextInput(TableDetailsComponentData.SYMBOL)
                        .value(this.data.getSymbol())
                        .label("Symbol")
                        .addValidator(new Required("Symbol is required!"))
                )
                .addWidget(new NumericInput(TableDetailsComponentData.WEIGHT)
                        .value(this.data.getWeight())
                        .label("Weight")
                        .format(Constants.ATOMIC_WEIGHT_FORMAT)
                        .addValidator(new Required("Weight is required!"))
                )
                .addWidget(Button.SAVE)
                .addWidget(Button.CANCEL)
                ;
    }
}
