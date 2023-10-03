package com.ssafy.coffeeing.modules.feed.scheduler;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.domain.FeedLike;
import com.ssafy.coffeeing.modules.feed.mapper.FeedLikeMapper;
import com.ssafy.coffeeing.modules.feed.repository.FeedLikeRepository;
import com.ssafy.coffeeing.modules.feed.repository.FeedRepository;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class FeedScheduler {

    private final RedisTemplate redisTemplate;
    private final FeedLikeRepository feedLikeRepository;
    private final FeedRepository feedRepository;
    private final MemberRepository memberRepository;
    private final String KEY = "feedLike";
    private List<FeedLike> insertFeedLikes;
    private List<FeedLike> deleteFeedLikes;
    private Set<Long> feedKeys;

    @Scheduled(fixedDelay = 25200000)
    public void writeBackFeedLikeInRedis() {
        HashOperations<String, Long, HashMap> hashOperations = redisTemplate.opsForHash();
        deleteFeedLikes = new ArrayList<>();
        insertFeedLikes = new ArrayList<>();
        feedKeys = hashOperations.keys(KEY);

        validateFeedIdWithWriteToDatabase(hashOperations);

        feedLikeRepository.deleteAll(deleteFeedLikes);
        feedLikeRepository.saveAll(insertFeedLikes);

        hashOperations.delete(KEY, feedKeys);
    }

    private void validateFeedIdWithWriteToDatabase(HashOperations<String, Long, HashMap> hashOperations) {
        for (Long feedId : feedKeys) {
            HashMap<Long, Boolean> feedLikeMap = hashOperations.get(KEY, feedId);
            Optional<Feed> feed = feedRepository.findById(feedId);
            feed.ifPresent(value -> validateMemberWithWriteDatabase(feedLikeMap, value));
        }
    }

    private void validateMemberWithWriteDatabase(HashMap<Long, Boolean> feedLikeMap, Feed feed) {
        if (Objects.nonNull(feedLikeMap)) {
            for (Long memberId : feedLikeMap.keySet()) {
                Optional<Member> member = memberRepository.findById(memberId);
                member.ifPresent(value -> insertToListByFeedLikeStatus(feedLikeMap, feed, memberId, value));
            }
        }
    }

    private void insertToListByFeedLikeStatus(
            HashMap<Long, Boolean> feedLikeMap,
            Feed feed,
            Long memberId,
            Member member) {
        if (feedLikeMap.get(memberId)) {
            if (feedLikeRepository.findFeedLikeByFeedAndMember(feed, member).isEmpty()) {
                insertFeedLikes.add(FeedLikeMapper.supplyFeedLikeEntityBy(feed, member));
            }
        } else {
            deleteFeedLikes.add(FeedLikeMapper.supplyFeedLikeEntityBy(feed, member));
        }
    }
}
