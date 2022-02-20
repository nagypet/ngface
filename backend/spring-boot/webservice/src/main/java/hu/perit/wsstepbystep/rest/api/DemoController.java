package hu.perit.wsstepbystep.rest.api;

import hu.perit.wsstepbystep.rest.model.BookDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
public class DemoController implements DemoApi
{
    //------------------------------------------------------------------------------------------------------------------
    // getDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public List<BookDTO> getDemoForm()
    {
        log.debug("getDemoForm()");
        return Collections.emptyList();
    }
}
