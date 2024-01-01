package hu.perit.ngface.sse.notification;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SseUpdateNotification extends SseNotification
{
    private final String subject;
    private final Set<Long> jobIds;

    public static SseUpdateNotification create(String subject, Long jobId)
    {
        return new SseUpdateNotification(subject, Set.of(jobId));
    }

    public SseUpdateNotification(String subject, Set<Long> jobIds)
    {
        super(Type.UPDATE);
        this.subject = subject;
        this.jobIds = new HashSet<>(jobIds);
    }

    public void append(SseUpdateNotification updateNotification)
    {
        this.jobIds.addAll(updateNotification.jobIds);
    }

}
