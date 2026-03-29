package hu.perit.ngface.sse.cluster;

import java.util.function.Consumer;

public final class NoopSseClusterBus implements SseClusterBus
{
    @Override
    public <T> void publish(String channel, T message)
    {
        // no-op
    }


    @Override
    public <T> void subscribe(String channel, Class<T> type, Consumer<T> handler)
    {
        // no-op
    }
}
