/*
 * Copyright 2020-2025 the original author or authors.
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

package hu.perit.ngface.webservice.ngface.widgetdemocomponent;

import hu.perit.ngface.core.formating.CurrencyFormat;
import hu.perit.ngface.core.formating.NumericFormat;
import hu.perit.ngface.core.view.ComponentView;
import hu.perit.ngface.core.widget.button.Button;
import hu.perit.ngface.core.widget.form.Form;
import hu.perit.ngface.core.widget.input.Autocomplete;
import hu.perit.ngface.core.widget.input.DateInput;
import hu.perit.ngface.core.widget.input.DateRangeInput;
import hu.perit.ngface.core.widget.input.NumericInput;
import hu.perit.ngface.core.widget.input.Select;
import hu.perit.ngface.core.widget.input.TextInput;
import hu.perit.ngface.core.widget.input.validator.Email;
import hu.perit.ngface.core.widget.input.validator.Max;
import hu.perit.ngface.core.widget.input.validator.Min;
import hu.perit.ngface.core.widget.input.validator.Pattern;
import hu.perit.ngface.core.widget.input.validator.Required;
import hu.perit.ngface.core.widget.input.validator.Size;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WidgetDemoComponentView implements ComponentView
{
    private final WidgetDemoComponentDTO data;


    @Override
    public Form getForm()
    {
        return new Form("widget-demo-form")
            .addWidget(new TextInput(WidgetDemoComponentDTO.OWNERS_NAME_ID)
                .value(this.data.getOwnersName())
                .label("Owner's name")
                .placeholder("Your name")
                .hint("Name of the account owner")
                .addValidator(new Required("Name is required!"))
                .addValidator(new Pattern("[A-Z]{1}[a-z]*", "Name has to begin with an uppercase letter and may contain only letters!"))
                .addValidator(new Size("The name must be max 15 chars long!").max(15))
            )
            .addWidget(new TextInput(WidgetDemoComponentDTO.PLACE_OF_BIRTH_ID)
                .value(this.data.getPlaceOfBirth())
                .label("Place of birth")
                .hint("Very long tooltip to see how it is rendered. kjh gkjhg kjhg kjhg kjgh kjhg kjhg kjhg kjhg kjhg kjhg kjg khjg kjhg kjh")
            )
            .addWidget(new TextInput(WidgetDemoComponentDTO.EMAIL_ID)
                .value(this.data.getEmail())
                .label("Email")
                .addValidator(new Required("Email address is required!"))
                .addValidator(new Email("Wrong email format!"))
            )
            .addWidget(new TextInput(WidgetDemoComponentDTO.ROLE_ID)
                .value(this.data.getRole())
                .label("User's role")
                .hint("Disabled control")
                .enabled(false)
            )
            .addWidget(new NumericInput(WidgetDemoComponentDTO.PRICE_ID)
                .label("Unit price")
                .hint("Must be between 0 and 99.999,99 EUR")
                .placeholder("Type in the unit price")
                .format(new CurrencyFormat("€").prefix("EUR"))
                .value(this.data.getPrice())
                .addValidator(new Required("Unit price is required!"))
                .addValidator(new Min(0.01, "The min unit price is 0,01 EUR"))
                .addValidator(new Max(99999.99, "The max unit price is 99.999,99 EUR"))
            )
            .addWidget(new NumericInput(WidgetDemoComponentDTO.COUNT_SAMPLES_ID)
                .label("Count of samples")
                .hint("Must be between 1-99")
                .placeholder("Type in the count of samples, you want to print")
                .format(new NumericFormat().precision(0).suffix("pcs"))
                .value(this.data.getCountSamples())
                .addValidator(new Required("Count of samples is required!"))
                .addValidator(new Min(1.0, "The count have to be between 1 and 99!"))
                .addValidator(new Max(99.0, "The count have to be between 1 and 99!"))
            )
            .addWidget(new TextInput(WidgetDemoComponentDTO.ACCOUNT_NO)
                .label("Account number")
                //.value("alma")
                .placeholder("00000000-00000000-00000000")
            )
            .addWidget(new DateInput(WidgetDemoComponentDTO.CHECK_IN_DATE_ID)
                .value(this.data.getCheckInDate())
                .label("Check-in")
                .hint("DateInput")
            )
            .addWidget(new DateInput(WidgetDemoComponentDTO.CHECK_OUT_DATE_ID)
                .value(this.data.getCheckOutDate())
                .label("Check-out")
                .hint("DateInput")
            )
            .addWidget(new DateRangeInput(WidgetDemoComponentDTO.DATE_RANGE_ID)
                .label("Check-in - Check-out")
                .data(this.data.getDateRange())
                .placeholder("Start date")
                .addValidator(new Required("Start date is required!"))
                .placeholder2("End date")
                .addValidator2(new Required("End date is required!"))
                .hint("DateRangeInput - date-range")
            )
            .addWidget(new DateRangeInput("date-range2")
                .label("Check-in - Check-out uninitialized")
                .hint("DateRangeInput - date-range2")
                .enabled(false)
            )
            .addWidget(new Select(WidgetDemoComponentDTO.SELECT_ID)
                .label("Select")
                .data(this.data.getSelectData())
                .placeholder("Select an option!")
                .hint("no default, required")
                .addValidator(new Required("Must select an option!"))
            )
            .addWidget(new Select(WidgetDemoComponentDTO.SELECT2_ID)
                .label("Select")
                .data(this.data.getSelect2Data())
                .placeholder("Select an option!")
                .hint("no default, not required")
            )
            .addWidget(new Select(WidgetDemoComponentDTO.SELECT3_ID)
                .label("Select")
                .data(this.data.getSelect3Data())
                .placeholder("Select an option!")
                .hint("First option is default")
            )
            .addWidget(new Autocomplete(WidgetDemoComponentDTO.AUTOCOMPLETE_ID)
                .label("Autocomplete")
                .data(this.data.getAutocompleteData())
                .placeholder("District")
                .hint("Which district?")
                .addValidator(new Required("District is mandatory!"))
            )
            .addWidget(new Autocomplete(WidgetDemoComponentDTO.AUTOCOMPLETE_STREETS_ID)
                .label("Street")
                .data(this.data.getAutocompleteStreetsData())
                .placeholder("Street")
                .hint("Which street?")
            )
            .addWidget(Button.OK.hint("OK button :-)"))
            .addWidget(Button.CANCEL)
            .addWidget(Button.DELETE);
    }
}
