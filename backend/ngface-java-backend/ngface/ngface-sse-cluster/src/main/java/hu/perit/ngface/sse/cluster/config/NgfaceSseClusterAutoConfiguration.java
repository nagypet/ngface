package hu.perit.ngface.sse.cluster.config;

import hu.perit.ngface.sse.cluster.RedisSseClusterBus;
import hu.perit.ngface.sse.cluster.SseClusterBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
@ConditionalOnProperty(prefix = "spring.cache", name = "type", havingValue = "redis")
@Slf4j
public class NgfaceSseClusterAutoConfiguration
{
    @Bean
    public RedisMessageListenerContainer ngfaceSseRedisMessageListenerContainer(RedisConnectionFactory connectionFactory)
    {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }


    @Bean
    public SseClusterBus sseClusterBus(
            RedisConnectionFactory connectionFactory,
            RedisMessageListenerContainer ngfaceSseRedisMessageListenerContainer
    )
    {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(connectionFactory);
        return new RedisSseClusterBus(redisTemplate, ngfaceSseRedisMessageListenerContainer);
    }
}
