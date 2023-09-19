package com.ssafy.coffeeing.modules.feed.service;

import com.ssafy.coffeeing.modules.event.eventer.ActivityConductedEvent;
import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.domain.FeedLike;
import com.ssafy.coffeeing.modules.feed.domain.FeedPage;
import com.ssafy.coffeeing.modules.feed.dto.*;
import com.ssafy.coffeeing.modules.feed.mapper.FeedLikeMapper;
import com.ssafy.coffeeing.modules.feed.mapper.FeedMapper;
import com.ssafy.coffeeing.modules.feed.repository.FeedLikeRepository;
import com.ssafy.coffeeing.modules.feed.repository.FeedRepository;
import com.ssafy.coffeeing.modules.feed.util.FeedUtil;
import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.FeedErrorInfo;
import com.ssafy.coffeeing.modules.global.exception.info.MemberErrorInfo;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.tag.domain.TagType;
import com.ssafy.coffeeing.modules.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final FeedLikeRepository feedLikeRepository;
    private final MemberRepository memberRepository;
    private final CapsuleRepository capsuleRepository;
    private final CoffeeRepository coffeeRepository;
    private final SecurityContextUtils securityContextUtils;
    private final FeedUtil feedUtil;
    private final ApplicationEventPublisher applicationEventPublisher;

    private static final int FEED_UPLOAD_EXPERIENCE = 75;


    @Transactional
    public UploadFeedResponse uploadFeedByMember(UploadFeedRequest uploadFeedRequest) {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();
        Feed feed;
        String imageUrl = feedUtil.makeImageElementToJsonString(uploadFeedRequest.images());
        String content = uploadFeedRequest.content();
        Tag tag = uploadFeedRequest.tag();

        if(Objects.nonNull(tag)) {
            validateTagInformation(tag);
            feed = FeedMapper.supplyFeedEntityOf(member, content, imageUrl, tag);
        } else {
            feed = FeedMapper.supplyFeedEntityOf(member, content, imageUrl);
        }

        applicationEventPublisher.publishEvent(new ActivityConductedEvent(FEED_UPLOAD_EXPERIENCE, member.getId()));

        return FeedMapper.supplyFeedResponseBy(feedRepository.save(feed));
    }

    @Transactional
    public void deleteFeedById(Long feedId) {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        Feed feed = feedRepository.findByIdAndMember(feedId, member)
                .orElseThrow(() -> new BusinessException(FeedErrorInfo.NOT_FOUND));

        feedRepository.delete(feed);
    }

    @Transactional
    public void updateFeedById(Long feedId, UpdateFeedRequest updateFeedRequest) {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();
        Tag tag = updateFeedRequest.tag();
        Feed feed = feedRepository.findByIdAndMember(feedId, member)
                .orElseThrow(() -> new BusinessException(FeedErrorInfo.NOT_FOUND));

        if(Objects.nonNull(tag)) {
            validateTagInformation(tag);
            feed.updateTag(tag);
            feed.updateContent(updateFeedRequest.content());
        } else {
            feed.updateContent(updateFeedRequest.content());
        }
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
    public ProfileFeedsResponse getMyFeeds(FeedsRequest feedsRequest) {
        Member owner = securityContextUtils.getCurrnetAuthenticatedMember();
        Long cursor = feedsRequest.cursor();
        Integer size = feedsRequest.size();

        return getProfileFeeds(owner, cursor, size);
    }

    @Transactional(readOnly = true)
    public FeedDetailResponse getFeedDetailById(Long feedId) {
        Member viewer = securityContextUtils.getMemberIdByTokenOptionalRequest();
        Feed feed = feedRepository.findFeedDetail(feedId)
                .orElseThrow(() -> new BusinessException(FeedErrorInfo.NOT_FOUND));

        List<ImageElement> images = feedUtil.makeJsonStringToImageElement(feed.getImageUrl());

        if (Objects.isNull(viewer)) {
            return getFeedDetailResponse(null, feed, Optional.empty(), images);
        }
        Optional<FeedLike> feedLike = feedLikeRepository.findFeedLikeByFeedAndMember(feed, viewer);
        return getFeedDetailResponse(viewer, feed, feedLike, images);
    }

    @Transactional(readOnly = true)
    public FeedPageResponse getFeedsByFeedPage(FeedsRequest feedsRequest) {
        Member viewer = securityContextUtils.getMemberIdByTokenOptionalRequest();
        List<FeedLike> feedLikes = new ArrayList<>();
        Long cursor = feedsRequest.cursor();
        Integer size = feedsRequest.size();

        Slice<Feed> feeds = feedRepository.findFeedsByFeedPage(cursor, PageRequest.of(0, size));
        if (!feeds.getContent().isEmpty()) {
            feedLikes = feedLikeRepository.findFeedLikesByFeedsAndMember(feeds.getContent(), viewer);
        }
        Long nextCursor = feeds.hasNext() ? feeds.getContent().get(size - 1).getId() : null;

        FeedPage feedPage = new FeedPage(feeds.getContent(), feedLikes, viewer, feedUtil);

        return FeedMapper.supplyFeedPageEntityOf(feedPage.feedPageElements, feeds.hasNext(), nextCursor);
    }

    private void validateTagInformation(Tag tag) {
        if(tag.category().equals(TagType.CAPSULE)) {
            boolean isExist = capsuleRepository.existsById(tag.tagId());
            if(!isExist) throw new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT);
        } else if(tag.category().equals(TagType.BEAN)) {
            boolean isExist = coffeeRepository.existsById(tag.tagId());
            if(!isExist) throw new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT);
        }
    }

    private FeedDetailResponse getFeedDetailResponse(
            Member viewer, Feed feed,
            Optional<FeedLike> feedLike,
            List<ImageElement> images) {
        Member feedWriter = feed.getMember();
        Tag tag = feed.getTagId() == null ? null : new Tag(feed.getTagId(), feed.getTagType(), feed.getTagName());

        if (Objects.isNull(viewer)) {
            return FeedMapper.supplyFeedDetailEntityOf(feed, tag, images, false, false);
        } else if (viewerLikedFeed(feedLike) && isFeedWrittenByViewer(feedWriter.getId(), viewer.getId())) {
            return FeedMapper.supplyFeedDetailEntityOf(feed, tag, images, true, true);
        } else if (viewerLikedFeed(feedLike)) {
            return FeedMapper.supplyFeedDetailEntityOf(feed, tag, images, true, false);
        } else if (isFeedWrittenByViewer(feedWriter.getId(), viewer.getId())) {
            return FeedMapper.supplyFeedDetailEntityOf(feed, tag, images, false, true);
        } else {
            return FeedMapper.supplyFeedDetailEntityOf(feed, tag, images, false, false);
        }
    }

    private boolean viewerLikedFeed(Optional<FeedLike> feedLike) {
        return feedLike.isPresent();
    }

    private boolean isFeedWrittenByViewer(Long feedWriterId, Long viewerId) {
        return Objects.equals(feedWriterId, viewerId);
    }

    private ProfileFeedsResponse getProfileFeeds(Member owner, Long cursor, Integer size) {
        Slice<FeedProjection> feeds = feedRepository
                .findFeedsByMemberAndPage(owner, cursor, PageRequest.of(0, size));

        Long nextCursor = feeds.hasNext() ? feeds.getContent().get(size - 1).getFeedId() : null;

        List<FeedElement> feedElements = feeds.getContent().stream()
                .map(feedProjection -> new FeedElement(feedProjection.getFeedId(),
                        feedUtil.makeJsonStringToImageElement(feedProjection.getImages())))
                .collect(Collectors.toList());

        return FeedMapper.supplyFeedEntityOf(feedElements, feeds.hasNext(), nextCursor);
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
