package hu.perit.ngface.sse.cluster;

import hu.perit.spvitamin.core.StackTracer;
import hu.perit.spvitamin.json.JSonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
public class RedisSseClusterBus implements SseClusterBus
{
    /**
     * Redis Pub/Sub topic prefix. A teljes topic: prefix + channel
     */
    public static final String DEFAULT_TOPIC_PREFIX = "ngface:sse:";

    private final StringRedisTemplate redisTemplate;
    private final RedisMessageListenerContainer listenerContainer;
    private final String topicPrefix;

    /**
     * Egy channelhez 1 listener-t regisztrálunk, amin belül a megfelelő típusra deszerializálunk és hívjuk a handlert.
     * (Ha több handler kell egy channelhez, ezt lehet később listává bővíteni.)
     */
    private final Map<String, MessageListener> listenersByChannel = new ConcurrentHashMap<>();


    public RedisSseClusterBus(StringRedisTemplate redisTemplate, RedisMessageListenerContainer listenerContainer)
    {
        this(redisTemplate, listenerContainer, DEFAULT_TOPIC_PREFIX);
    }


    public RedisSseClusterBus(
            StringRedisTemplate redisTemplate,
            RedisMessageListenerContainer listenerContainer,
            String topicPrefix
    )
    {
        this.redisTemplate = Objects.requireNonNull(redisTemplate, "redisTemplate must not be null");
        this.listenerContainer = Objects.requireNonNull(listenerContainer, "listenerContainer must not be null");
        this.topicPrefix = (topicPrefix == null || topicPrefix.isBlank()) ? DEFAULT_TOPIC_PREFIX : topicPrefix;
    }


    @Override
    public <T> void publish(String channel, T message)
    {
        if (channel == null || channel.isBlank() || message == null)
        {
            return;
        }

        String topic = topicName(channel);

        try
        {
            String payload = JSonSerializer.toJson(message);
            this.redisTemplate.convertAndSend(topic, payload);
        }
        catch (Exception ex)
        {
            // Pub/Sub kiesés nem blokkolhatja a lokális működést
            log.warn("Failed to publish SSE cluster message to topic='{}': {}", topic, ex.getMessage());
            log.debug(StackTracer.toString(ex));
        }
    }


    @Override
    public <T> void subscribe(String channel, Class<T> type, Consumer<T> handler)
    {
        if (channel == null || channel.isBlank())
        {
            throw new IllegalArgumentException("channel must not be blank");
        }
        if (type == null)
        {
            throw new IllegalArgumentException("type must not be null");
        }
        if (handler == null)
        {
            throw new IllegalArgumentException("handler must not be null");
        }

        listenersByChannel.computeIfAbsent(channel, ch -> {
            String topic = topicName(ch);

            MessageListener listener = (message, pattern) -> {
                try
                {
                    String json = extractBodyAsString(message);
                    if (json == null || json.isBlank())
                    {
                        return;
                    }

                    T obj = JSonSerializer.fromJson(json, type);
                    handler.accept(obj);
                }
                catch (Exception ex)
                {
                    log.warn("Failed to handle SSE cluster message on topic='{}': {}", topic, ex.getMessage());
                    log.debug(StackTracer.toString(ex));
                }
            };

            this.listenerContainer.addMessageListener(listener, new ChannelTopic(topic));
            log.info("Subscribed to Redis topic '{}' for channel '{}', type={}", topic, ch, type.getName());
            return listener;
        });
    }


    private String topicName(String channel)
    {
        return this.topicPrefix + channel;
    }


    private static String extractBodyAsString(Message message)
    {
        if (message == null || message.getBody() == null)
        {
            return null;
        }
        return new String(message.getBody(), StandardCharsets.UTF_8);
    }
}
