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

        List<String> values = redisTemplate.opsForList()
                .range(KEY_IDENTIFIER + key, 0, length);

        if (values == null || values.isEmpty()) {
            throw new BusinessException(InfraErrorInfo.NO_CACHE);
        }

        return values.stream().map(Long::valueOf).toList();
    }

    public void pushAll(String key, List<Long> ids) {

        redisTemplate.opsForList().rightPushAll(KEY_IDENTIFIER + key, ids.stream().map(String::valueOf).toList());
        redisTemplate.expire(KEY_IDENTIFIER + key, KEY_EXPIRATION, TimeUnit.HOURS);
    }
}
