package com.ssafy.coffeeing.modules.feed.util;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.domain.FeedLike;
import com.ssafy.coffeeing.modules.feed.repository.FeedLikeRepository;
import com.ssafy.coffeeing.modules.member.domain.Member;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class FeedRedisUtil {

    private final RedisTemplate redisTemplate;
    private final FeedLikeRepository feedLikeRepository;

    private final String KEY = "feedLike";

    public FeedRedisUtil(RedisTemplate redisTemplate, FeedLikeRepository feedLikeRepository) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(Long.class));
        this.feedLikeRepository = feedLikeRepository;
    }

    public boolean isLikedFeedInRedis(Feed feed, Member member) {
        HashOperations<String, Long, HashMap> hashOperations = redisTemplate.opsForHash();
        HashMap<Long, Boolean> feedLikeCache;

        if (hashOperations.hasKey(KEY, feed.getId())) {
            feedLikeCache = hashOperations.get(KEY, feed.getId());
            if (!feedLikeCache.containsKey(member.getId())) {
                setFeedLikeStatus(feed, member, hashOperations, feedLikeCache);
            }
        } else { // Read Through 전략
            feedLikeCache = new HashMap<>();
            setFeedLikeStatus(feed, member, hashOperations, feedLikeCache);

            if (isNotSetExpireTime()) {
                redisTemplate.expire(KEY, 8, TimeUnit.HOURS);
            }
        }
        return feedLikeCache.get(member.getId());
    }

    private void setFeedLikeStatus(
            Feed feed,
            Member member,
            HashOperations<String, Long, HashMap> hashOperations,
            HashMap<Long, Boolean> feedLikeCache) {
        Optional<FeedLike> feedLike = feedLikeRepository.findFeedLikeByFeedAndMember(feed, member);

        if (feedLike.isPresent()) {
            feedLikeCache.put(member.getId(), true);
        } else {
            feedLikeCache.put(member.getId(), false);
        }
        hashOperations.put(KEY, feed.getId(), feedLikeCache);
    }

    public boolean isNotLikedFeedInRedis(Feed feed, Member member) {
        return !isLikedFeedInRedis(feed, member);
    }

    public void disLikeFeedInRedis(Feed feed, Member member) {
        HashOperations<String, Long, HashMap> hashOperations = redisTemplate.opsForHash();

        HashMap<Long, Boolean> feedLikeCache = hashOperations.get(KEY, feed.getId());

        if (Objects.nonNull(feedLikeCache)) {
            feedLikeCache.put(member.getId(), false);
            hashOperations.put(KEY, feed.getId(), feedLikeCache);
        }
    }

    public void likeFeedInRedis(Feed feed, Member member) {
        HashOperations<String, Long, HashMap> hashOperations = redisTemplate.opsForHash();

        HashMap<Long, Boolean> feedLikeCache = hashOperations.get(KEY, feed.getId());

        if(Objects.nonNull(feedLikeCache)) {
            feedLikeCache.put(member.getId(), true);
            hashOperations.put(KEY, feed.getId(), feedLikeCache);
        }
    }

    private boolean isNotSetExpireTime() {
        if (Objects.nonNull(redisTemplate.getExpire(KEY))) {
            return redisTemplate.getExpire(KEY).equals(-1L);
        }
        return false;
    }
}
