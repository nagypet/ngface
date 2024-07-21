package hu.perit.ngface.webservice.scheduler;

import hu.perit.ngface.sse.SseService;
import hu.perit.ngface.sse.notification.SseUpdateNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TaskRunner
{
    private final SseService sseService;


    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 1)
    void sendNotification()
    {
        this.sseService.sendNotification(SseUpdateNotification.create("tick", 0L));
    }
}
