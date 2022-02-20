package hu.perit.wsstepbystep.rest.api;

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
                        .placeholder("Your name")
                        .addConstraint(new Required("Name is required!")));
    }
}
