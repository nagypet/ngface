package hu.perit.ngface.sse.rest;

import hu.perit.ngface.sse.ServerSentEvent;
import hu.perit.ngface.sse.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class NgfaceSseRestController implements NgfaceSseRestApi
{
    private final SseService sseService;

    @Override
    public ResponseEntity<SseEmitter> subscribe(String lastEventId)
    {
        ServerSentEvent.Subscription<String> subscription = this.sseService.subscribe(lastEventId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_EVENT_STREAM);
        return new ResponseEntity<>(subscription, headers, HttpStatus.OK);
    }
}
