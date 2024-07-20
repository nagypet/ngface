package hu.perit.ngface.sse;

import hu.perit.ngface.sse.notification.SseNotification;
import hu.perit.spvitamin.spring.config.SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class NotificationWatchDog
{
    @Async
    public void resendInMillis(long sleepTime, SseNotification sseNotification)
    {
        // To avoid circular dependencies
        SseServiceImpl sseService = (SseServiceImpl) SpringContext.getBean(SseService.class);
        try
        {
            if (sleepTime > 0)
            {
                TimeUnit.MILLISECONDS.sleep(sleepTime);
            }
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
        log.debug("Fireing trigger event {}", sseNotification);
        sseService.resendNotification(sseNotification);
    }
}
