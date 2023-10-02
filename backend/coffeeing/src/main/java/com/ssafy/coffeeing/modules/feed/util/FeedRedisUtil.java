package com.ssafy.coffeeing.modules.feed.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class FeedRedisUtil {

    private final RedisTemplate redisTemplate;

    public FeedRedisUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(Long.class));
        this.redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(HashMap.class));
    }
}
