package hu.perit.wsstepbystep.rest.api;

import hu.perit.ngface.widget.button.Button;
import hu.perit.ngface.widget.form.Form;
import hu.perit.ngface.widget.input.TextInput;
import hu.perit.ngface.widget.input.constraint.Required;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

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
                        .tooltip("Name of the account owner")
                        .addConstraint(new Required("Name is required!"))
                )
                .addWidget(new TextInput("place-of-birth")
                        .label("Place of birth")
                )
                .addWidget(Button.OK)
                .addWidget(Button.CANCEL)
                .addWidget(Button.DELETE)
                ;
    }
}
