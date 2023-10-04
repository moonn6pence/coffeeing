package com.ssafy.coffeeing.modules.curation.util;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.InfraErrorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class PopularProductCacheUtil {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String KEY_IDENTIFIER = "top12-";
    private static final String KEY_CAPSULE = "capsule:";
    private static final String KEY_COFFEE = "coffee:";
    private static final Long KEY_EXPIRATION = 1L;


    public List<Long> getAll(boolean isCapsule, Integer length) {

        String key = KEY_IDENTIFIER + (isCapsule ? KEY_CAPSULE : KEY_COFFEE);

        Long size = redisTemplate.opsForList().size(key);

        if (size == null || size == 0) {
            throw new BusinessException(InfraErrorInfo.NO_CACHE);
        }

        List<String> values = redisTemplate.opsForList()
                .range(KEY_IDENTIFIER + (isCapsule ? KEY_CAPSULE : KEY_COFFEE), 0, Long.min(size, length));

        return values.stream().map(Long::valueOf).toList();
    }

    public void pushAll(boolean isCapsule, List<String> ids) {

        String key = KEY_IDENTIFIER + (isCapsule ? KEY_CAPSULE : KEY_COFFEE);

        redisTemplate.opsForList().rightPushAll(key, ids);
        redisTemplate.expire(key, KEY_EXPIRATION, TimeUnit.HOURS);

    }


}
