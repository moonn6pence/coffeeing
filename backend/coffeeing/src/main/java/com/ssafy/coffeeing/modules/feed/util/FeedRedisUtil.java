package com.ssafy.coffeeing.modules.feed.util;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.member.domain.Member;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class FeedRedisUtil {

    private final RedisTemplate redisTemplate;

    public FeedRedisUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(Long.class));
    }

    public boolean isLikedFeedInRedis(Feed feed, Member member) {
        HashOperations<String, Long, HashMap> hashOperations = redisTemplate.opsForHash();

        if (hashOperations.hasKey("feedLike", feed.getId())) {
            Map<Long, Boolean> feedLikeCache = hashOperations.get("feedLike", feed.getId());

            if (feedLikeCache.containsKey(member.getId())) {
                return feedLikeCache.get(member.getId());
            }
            return false;
        }
        return false;
    }

    public boolean isNotLikedFeedInRedis(Feed feed, Member member) {
        return !isLikedFeedInRedis(feed, member);
    }

    public void disLikeFeedInRedis(Feed feed, Member member) {
        HashOperations<String, Long, HashMap> hashOperations = redisTemplate.opsForHash();
        HashMap<Long, Boolean> feedLikeCache;

        if (hashOperations.hasKey("feedLike", feed.getId())) {
            feedLikeCache = hashOperations.get("feedLike", feed.getId());
            feedLikeCache.put(member.getId(), false);
        } else {
            feedLikeCache = new HashMap<>();
            feedLikeCache.put(member.getId(), false);
        }
        hashOperations.put("feedLike", feed.getId(), feedLikeCache);

        if (isNotSetExpireTime()) {
            redisTemplate.expire("feedLike", 8, TimeUnit.HOURS);
        }
    }

    public void likeFeedInRedis(Feed feed, Member member) {
        HashOperations<String, Long, HashMap> hashOperations = redisTemplate.opsForHash();
        HashMap<Long, Boolean> feedLikeCache;

        if (hashOperations.hasKey("feedLike", feed.getId())) {
            feedLikeCache = hashOperations.get("feedLike", feed.getId());
            feedLikeCache.put(member.getId(), true);
        } else {
            feedLikeCache = new HashMap<>();
            feedLikeCache.put(member.getId(), true);
        }
        hashOperations.put("feedLike", feed.getId(), feedLikeCache);

        if (isNotSetExpireTime()) {
            redisTemplate.expire("feedLike", 8, TimeUnit.HOURS);
        }
    }

    private boolean isNotSetExpireTime() {
        return redisTemplate.getExpire("feedLike").equals(-1L);
    }
}
