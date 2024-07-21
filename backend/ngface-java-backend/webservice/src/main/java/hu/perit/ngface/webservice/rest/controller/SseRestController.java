package hu.perit.ngface.webservice.rest.controller;

import hu.perit.ngface.sse.SseService;
import hu.perit.ngface.sse.rest.NgfaceSseRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SseRestController extends NgfaceSseRestController
{
    public SseRestController(SseService sseService)
    {
        super(sseService);
    }


    //------------------------------------------------------------------------------------------------------------------
    // subscribe()
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/frontend/sse/subscribe")
    @Override
    public ResponseEntity<SseEmitter> subscribe(String lastEventId)
    {
        return super.subscribe(lastEventId);
    }
}
