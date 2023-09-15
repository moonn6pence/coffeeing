package com.ssafy.coffeeing.modules.feed.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.domain.FeedLike;
import com.ssafy.coffeeing.modules.feed.dto.*;
import com.ssafy.coffeeing.modules.feed.mapper.FeedLikeMapper;
import com.ssafy.coffeeing.modules.feed.mapper.FeedMapper;
import com.ssafy.coffeeing.modules.feed.repository.FeedLikeRepository;
import com.ssafy.coffeeing.modules.feed.repository.FeedRepository;
import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.FeedErrorInfo;
import com.ssafy.coffeeing.modules.global.exception.info.MemberErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final FeedLikeRepository feedLikeRepository;
    private final MemberRepository memberRepository;
    private final SecurityContextUtils securityContextUtils;

    @Transactional
    public UploadFeedResponse uploadFeedByMember(UploadFeedRequest uploadFeedRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        try{
            String imageUrl = objectMapper.writeValueAsString(uploadFeedRequest.images());
            Feed feed = FeedMapper.supplyFeedEntityBy(member, uploadFeedRequest.content(), imageUrl);
            return FeedMapper.supplyFeedResponseBy(feedRepository.save(feed));
        } catch (JsonProcessingException e) {
            throw new BusinessException(FeedErrorInfo.FEED_IMAGES_TO_JSON_STRING_ERROR);
        }
    }

    @Transactional
    public void deleteFeedById(Long feedId) {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        Feed feed = feedRepository.findByIdAndMember(feedId, member)
                .orElseThrow(() -> new BusinessException(FeedErrorInfo.NOT_FOUND));

        feedRepository.delete(feed);
    }

    @Transactional
    public void updateFeedContentById(Long feedId, UpdateFeedRequest updateFeedRequest) {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        Feed feed = feedRepository.findByIdAndMember(feedId, member)
                .orElseThrow(() -> new BusinessException(FeedErrorInfo.NOT_FOUND));

        feed.updateContent(updateFeedRequest.content());
    }

    @Transactional
    public ToggleResponse toggleFeedLike(Long feedId) {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new BusinessException(FeedErrorInfo.NOT_FOUND));

        Optional<FeedLike> feedLike = feedLikeRepository.findFeedLikeByFeedAndMember(feed, member);

        if(feedLike.isPresent()) {
            feedLikeRepository.delete(feedLike.get());
            return decreaseFeedLikeCount(feed);
        } else {
            feedLikeRepository.save(FeedLikeMapper.supplyFeedLikeEntityBy(feed, member));
            return increaseFeedLikeCount(feed);
        }
    }

    @Transactional(readOnly = true)
    public ProfileFeedsResponse getFeedsByMemberId(MemberFeedsRequest memberFeedsRequest) {
        Member owner = memberRepository.findById(memberFeedsRequest.memberId())
                .orElseThrow(() -> new BusinessException(MemberErrorInfo.NOT_FOUND));

        return getProfileFeeds(owner, memberFeedsRequest.cursor(), memberFeedsRequest.size());
    }

    @Transactional(readOnly = true)
    public ProfileFeedsResponse getMyFeeds(MyFeedsRequest myFeedsRequest) {
        Member owner = securityContextUtils.getCurrnetAuthenticatedMember();
        Long cursor = myFeedsRequest.cursor();
        Integer size = myFeedsRequest.size();

        return getProfileFeeds(owner, cursor, size);
    }

    private ProfileFeedsResponse getProfileFeeds(Member owner, Long cursor, Integer size) {
        Slice<FeedProjection> feeds = feedRepository
                .findFeedsByMemberAndPage(owner, cursor, PageRequest.of(0, size));

        Long nextCursor = feeds.hasNext() ? feeds.getContent().get(size - 1).getFeedId() : null;

        return FeedMapper.supplyFeedEntityOf(feeds, nextCursor);
    }

    private ToggleResponse decreaseFeedLikeCount(Feed feed) {
        feed.decreaseLikeCount();
        return FeedLikeMapper.supplyFeedLikeResponseBy(false);
    }

    private ToggleResponse increaseFeedLikeCount(Feed feed) {
        feed.increaseLikeCount();
        return FeedLikeMapper.supplyFeedLikeResponseBy(true);
    }
}
