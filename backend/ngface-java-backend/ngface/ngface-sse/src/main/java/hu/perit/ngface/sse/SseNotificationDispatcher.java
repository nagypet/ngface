package hu.perit.ngface.sse;


import hu.perit.ngface.sse.notification.SseNotification;

public interface SseNotificationDispatcher
{
    boolean handlesNotification(SseNotification notification);
    void dispatch(SseNotification notification);
    default void triggerEventFired(SseNotification notification) {}
}
