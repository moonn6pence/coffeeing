package com.ssafy.coffeeing.modules.recommend.util;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.InfraErrorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RecommendCacheUtil {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String KEY_IDENTIFIER = "rec_sys:";
    private static final Long KEY_EXPIRATION = 12L;

    public List<Long> getAll(String key, Integer length) {

        key = KEY_IDENTIFIER + key;

        Long size = redisTemplate.opsForList().size(key);

        if (size == null || size == 0) {
            throw new BusinessException(InfraErrorInfo.NO_CACHE);
        }

        List<String> values = redisTemplate.opsForList().range(key, 0, Long.min(size, length));

        return values.stream().map(Long::valueOf).toList();
    }

    public void pushAll(String key, List<String> ids) {

        key = KEY_IDENTIFIER + key;

        redisTemplate.opsForList().rightPushAll(key, ids);
        redisTemplate.expire(key, KEY_EXPIRATION, TimeUnit.HOURS);
    }
}
