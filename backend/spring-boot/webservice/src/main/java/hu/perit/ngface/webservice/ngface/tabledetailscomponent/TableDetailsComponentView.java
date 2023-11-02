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

package hu.perit.ngface.webservice.ngface.tabledetailscomponent;

import hu.perit.ngface.core.formating.NumericFormat;
import hu.perit.ngface.core.view.ComponentView;
import hu.perit.ngface.core.widget.button.Button;
import hu.perit.ngface.core.widget.form.Form;
import hu.perit.ngface.core.widget.input.Autocomplete;
import hu.perit.ngface.core.widget.input.NumericInput;
import hu.perit.ngface.core.widget.input.TextInput;
import hu.perit.ngface.core.widget.input.validator.Max;
import hu.perit.ngface.core.widget.input.validator.Min;
import hu.perit.ngface.core.widget.input.validator.Required;
import hu.perit.ngface.core.widget.input.validator.Size;
import hu.perit.ngface.webservice.model.AddressTableRow;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TableDetailsComponentView implements ComponentView
{
    public static final String INVALID_LENGTH = "Invalid length!";

    private final TableDetailsComponentDTO data;


    @Override
    public Form getForm()
    {
        return new Form(data.getId())
            .title(String.format("%s %s", this.data.getPostCode(), this.data.getName()))
            .addWidget(new NumericInput(AddressTableRow.COL_POSTCODE)
                .value(this.data.getPostCode())
                .label("Post code")
                .format(NumericFormat.UNGROUPED)
                .addValidator(new Required("Post code is required!"))
                .addValidator(new Min(1000.0, "Postal code must be between 1000 and 9999"))
                .addValidator(new Max(9999.0, "Postal code must be between 1000 and 9999"))
            )
            .addWidget(new TextInput(AddressTableRow.COL_CITY)
                .value(this.data.getCity())
                .label("City")
                .addValidator(new Required("City is required!"))
                .addValidator(new Size(INVALID_LENGTH).min(2).max(20))
            )
            .addWidget(new TextInput(AddressTableRow.COL_STREET)
                .value(this.data.getStreet())
                .label("Street")
                .addValidator(new Required("Street is required!"))
                .addValidator(new Size(INVALID_LENGTH).min(2).max(30))
            )
            .addWidget(new Autocomplete(AddressTableRow.COL_DISTRICT)
                .data(this.data.getDistrict())
                .label("District")
                .addValidator(new Required("District is required!"))
                .addValidator(new Size(INVALID_LENGTH).min(2).max(20))
            )
            .addWidget(Button.SAVE)
            .addWidget(Button.CANCEL)
            ;
    }
}
