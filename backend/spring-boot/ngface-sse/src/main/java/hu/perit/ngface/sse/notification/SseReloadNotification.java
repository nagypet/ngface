package hu.perit.ngface.sse.notification;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SseReloadNotification extends SseNotification
{
    public static SseReloadNotification create()
    {
        return new SseReloadNotification();
    }

    public SseReloadNotification()
    {
        super(Type.RELOAD);
    }
}
