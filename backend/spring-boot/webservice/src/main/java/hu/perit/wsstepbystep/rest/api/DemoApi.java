package hu.perit.wsstepbystep.rest.api;

import hu.perit.ngface.widget.form.Form;
import org.springframework.web.bind.annotation.GetMapping;

public interface DemoApi
{
    String BASE_URL_DEMO = "/demo";

    //------------------------------------------------------------------------------------------------------------------
    // getDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(BASE_URL_DEMO)
    Form getDemoForm();
}
