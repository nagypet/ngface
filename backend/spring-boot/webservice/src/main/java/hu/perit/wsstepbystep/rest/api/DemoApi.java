package hu.perit.wsstepbystep.rest.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import hu.perit.wsstepbystep.rest.model.BookDTO;

public interface DemoApi
{
    String BASE_URL_DEMO = "/demo";

    //------------------------------------------------------------------------------------------------------------------
    // getDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(BASE_URL_DEMO)
    List<BookDTO> getDemoForm();
}
