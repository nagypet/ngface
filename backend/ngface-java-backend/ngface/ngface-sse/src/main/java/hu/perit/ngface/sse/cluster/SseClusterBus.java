package hu.perit.ngface.sse.cluster;

import java.util.function.Consumer;

/**
 * The SseClusterBus interface provides a mechanism for managing inter-process communication
 * through a pub/sub model. It allows publishing messages to specific channels and subscribing
 * to receive messages from those channels. This abstraction is intended for use in clustered
 * environments where event-based communication is necessary.
 *
 * @param <T> The type of messages being handled.
 */
public interface SseClusterBus
{
    <T> void publish(String channel, T message);

    <T> void subscribe(String channel, Class<T> type, Consumer<T> handler);
}
