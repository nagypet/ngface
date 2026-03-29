package hu.perit.ngface.sse.cluster;

import hu.perit.ngface.sse.notification.SseNotification;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ClusterNotification
{
    private final String origin;
    private final SseNotification sseNotification;
}
