package hu.perit.wsstepbystep.rest.api;

import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.button.Button;
import hu.perit.ngface.widget.form.Form;
import hu.perit.ngface.widget.input.DateInput;
import hu.perit.ngface.widget.input.DateRangeInput;
import hu.perit.ngface.widget.input.NumericInput;
import hu.perit.ngface.widget.input.TextInput;
import hu.perit.ngface.widget.input.validator.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@Slf4j
public class DemoController implements DemoApi
{
    //------------------------------------------------------------------------------------------------------------------
    // getDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public Form getDemoForm()
    {
        log.debug("getDemoForm()");

        return new Form("demo-form")
                .addWidget(new TextInput("name")
                        .value("Peter")
                        .label("Owner's name")
                        .placeholder("Your name")
                        .hint("Name of the account owner")
                        .addValidator(new Required("Name is required!"))
                        .addValidator(new Pattern("[A-Z]{1}[a-z]*", "Name has to begin with an uppercase letter and may contain only letters!"))
                        .addValidator(new Size("The name must be max 15 chars long!").max(15))
                )
                .addWidget(new TextInput("place-of-birth")
                        .label("Place of birth")
                        .hint("Very long tooltip to see how it is rendered. kjh gkjhg kjhg kjhg kjgh kjhg kjhg kjhg kjhg kjhg kjhg kjg khjg kjhg kjh")
                )
                .addWidget(new TextInput("email")
                        .label("Email")
                        .addValidator(new Required("Email address is required!"))
                        .addValidator(new Email("Wrong email format!"))
                )
                .addWidget(new TextInput("role")
                        .value("Admin")
                        .label("User's role")
                        .hint("Disabled control")
                        .enabled(false)
                )
                .addWidget(new NumericInput("amount")
                        .label("Amount")
                        .hint("Must be between 0 and 99.999 EUR")
                        .placeholder("Type in the amount")
                        .precision(2)
                        .value(new BigDecimal("2.123456789"))
                        .prefix("EUR")
                        .addValidator(new Required("Amount is required!"))
                        .addValidator(new Min(0.01, "The min amount is 0,01 EUR"))
                        .addValidator(new Max(99999.99, "The max amount is 99.999,99 EUR"))
                )
                .addWidget(new NumericInput("count-samples")
                        .label("Count of samples")
                        .hint("Must be between 1-99")
                        .placeholder("Type in the count of samples, you want to print")
                        .precision(0)
                        .value(new BigDecimal("10"))
                        .suffix("pcs")
                        .addValidator(new Required("Count of samples is required!"))
                        .addValidator(new Min(1.0, "The count have to be between 1 and 99!"))
                        .addValidator(new Max(99.0, "The count have to be between 1 and 99!"))
                )
                .addWidget(new DateInput("check-in-date")
                        .label("Check-in")
                        .value(LocalDate.now())
                        .hint("DateInput")
                )
                .addWidget(new DateInput("check-out-date")
                        .label("Check-out")
                        .hint("DateInput")
                )
                .addWidget(new DateRangeInput("date-range")
                        .label("Check-in - Check-out")
                        .data(new DateRangeInput.Data(LocalDate.now(), LocalDate.now().plusDays(1)))
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
                .addWidget(Button.OK.hint("OK button :-)"))
                .addWidget(Button.CANCEL)
                .addWidget(Button.DELETE)
                ;
    }

    @Override
    public void submitDemoForm(SubmitFormData submitFormData)
    {
        log.debug("submitDemoForm()");

        if (submitFormData.getWidgetDataMap() != null)
        {
            for (Map.Entry<String, WidgetData> entry : submitFormData.getWidgetDataMap().entrySet())
            {
                log.debug(String.format("%s: %s", entry.getKey(), entry.getValue().toString()));
            }
        }
    }
}
