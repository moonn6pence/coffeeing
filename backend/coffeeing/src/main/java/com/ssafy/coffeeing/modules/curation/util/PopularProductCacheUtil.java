package com.ssafy.coffeeing.modules.curation.util;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.InfraErrorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PopularProductCacheUtil {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String KEY_IDENTIFIER = "top12:";
    private static final String KEY_CAPSULE = "capsule";
    private static final String KEY_COFFEE = "coffee";

    public List<Long> getAll(Boolean isCapsule, Integer length) {

        List<String> values = redisTemplate.opsForList()
                .range(KEY_IDENTIFIER + (isCapsule ? KEY_CAPSULE : KEY_COFFEE), 0, length);

        if (values == null) {
            throw new BusinessException(InfraErrorInfo.NO_CACHE);
        }

        return values.stream().map(Long::valueOf).toList();
    }

    public void pushAll(Boolean isCapsule, List<Long> ids) {

        redisTemplate.opsForList().rightPushAll(KEY_IDENTIFIER + (isCapsule ? KEY_CAPSULE : KEY_COFFEE),
                ids.stream().map(String::valueOf).toList());
    }


}
