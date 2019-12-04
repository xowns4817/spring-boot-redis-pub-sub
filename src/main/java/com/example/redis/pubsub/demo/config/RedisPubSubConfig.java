package com.example.redis.pubsub.demo.config;

import com.example.redis.pubsub.demo.RedisMessagePublisher;
import com.example.redis.pubsub.demo.RedisMessageSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisPubSubConfig {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisPubSubConfig(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // pub, sub 모델을 사용하기 위해서, 메시지 큐를 위한 Bean을 추가한다.
    // MessageListenerAdapter Bean은 pub sub 모델에서 subscriber 역할을 수행한다.
    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new RedisMessageSubscriber());
    }

    // Channel의 메시지를 받는데 사용되는 컨테이너를 구현한다.
    // RedisMessageListenerContainer는 Spring Data Redis에서 제공하는 클래스이다.
    // 컨테이너는 Redis 채널로 부터 메시지를 받는데 사용하며, 구독자들에게 메시지를 dispatch 하는 역할을 한다.
    // 즉, 메시지를 수신하는데 관련한 비지니스 로직을 작성할 수 있다.
    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisTemplate.getConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        return container;
    }

    @Bean  // 메시지를 게시한다.
    RedisMessagePublisher redisPublisher() {
        return new RedisMessagePublisher(redisTemplate, topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("taejun");
    }
}
