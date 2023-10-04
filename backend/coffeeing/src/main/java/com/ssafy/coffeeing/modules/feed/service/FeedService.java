package com.ssafy.coffeeing.modules.feed.service;

import com.ssafy.coffeeing.modules.event.eventer.ExperienceEvent;
import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.domain.FeedPage;
import com.ssafy.coffeeing.modules.feed.dto.*;
import com.ssafy.coffeeing.modules.feed.mapper.FeedLikeMapper;
import com.ssafy.coffeeing.modules.feed.mapper.FeedMapper;
import com.ssafy.coffeeing.modules.feed.repository.FeedLikeRepository;
import com.ssafy.coffeeing.modules.feed.repository.FeedRepository;
import com.ssafy.coffeeing.modules.feed.util.FeedRedisUtil;
import com.ssafy.coffeeing.modules.feed.util.FeedUtil;
import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.FeedErrorInfo;
import com.ssafy.coffeeing.modules.global.exception.info.MemberErrorInfo;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.ProductType;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.search.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
    private final FeedRedisUtil feedRedisUtil;
    private static final int FEED_UPLOAD_EXPERIENCE = 75;


    @Transactional
    public UploadFeedResponse uploadFeedByMember(UploadFeedRequest uploadFeedRequest) {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();
        Feed feed;
        String imageUrl = feedUtil.makeImageElementToJsonString(uploadFeedRequest.images());
        String content = uploadFeedRequest.content();
        Tag tag = uploadFeedRequest.tag();

        if(Objects.nonNull(tag)) {
            attachFeedTagWithValidation(tag);
            feed = FeedMapper.supplyFeedEntityOf(member, content, imageUrl, tag);
        } else {
            feed = FeedMapper.supplyFeedEntityOf(member, content, imageUrl);
        }

        applicationEventPublisher.publishEvent(new ExperienceEvent(FEED_UPLOAD_EXPERIENCE, member.getId()));

        return FeedMapper.supplyFeedResponseBy(feedRepository.save(feed));
    }

    @Transactional
    public void deleteFeedById(Long feedId) {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        Feed feed = feedRepository.findByIdAndMember(feedId, member)
                .orElseThrow(() -> new BusinessException(FeedErrorInfo.NOT_FOUND));
        detachFeedTagWithValidation(feed);
        feedLikeRepository.deleteFeedLikesByFeed(feed);
        feedRedisUtil.disLikeFeedInRedis(feed);
        feedRepository.delete(feed);
    }

    @Transactional
    public void updateFeedById(Long feedId, UpdateFeedRequest updateFeedRequest) {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();
        Tag tag = updateFeedRequest.tag();
        Feed feed = feedRepository.findByIdAndMember(feedId, member)
                .orElseThrow(() -> new BusinessException(FeedErrorInfo.NOT_FOUND));

        if(Objects.nonNull(tag)) {
            compareTagInformationByNewTag(feed, tag);
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
        boolean feedLikeResponse = false;

        if(feedRedisUtil.isLikedFeedInRedis(feed, member)) {
            feedRedisUtil.disLikeFeedInRedis(feed, member);
            feedLikeResponse = decreaseFeedLikeCount(feed);
        } else if(feedRedisUtil.isNotLikedFeedInRedis(feed, member)) {
            feedRedisUtil.likeFeedInRedis(feed, member);
            feedLikeResponse = increaseFeedLikeCount(feed);
        }

        return FeedLikeMapper.supplyFeedLikeResponseBy(feedLikeResponse);
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
        int likeCount = feedRedisUtil.getFeedLikeCount(feed);

        if (Objects.isNull(viewer)) {
            return getFeedDetailResponse(null, feed, likeCount, false, images);
        }
        boolean isLikeFeed = feedRedisUtil.isLikedFeedInRedis(feed, viewer);

        return getFeedDetailResponse(viewer, feed, likeCount, isLikeFeed, images);
    }

    @Transactional(readOnly = true)
    public FeedPageResponse getFeedsByFeedPage(FeedsRequest feedsRequest) {
        Member viewer = securityContextUtils.getMemberIdByTokenOptionalRequest();
        Long cursor = feedsRequest.cursor();
        Integer size = feedsRequest.size();

        Slice<Feed> feeds = feedRepository.findFeedsByFeedPage(cursor, PageRequest.of(0, size));
        Long nextCursor = feeds.hasNext() ? feeds.getContent().get(size - 1).getId() : null;

        FeedPage feedPage = new FeedPage(feeds.getContent(), feedRedisUtil, viewer, feedUtil);

        return FeedMapper.supplyFeedPageEntityOf(feedPage.feedPageElements, feeds.hasNext(), nextCursor);
    }

    private void compareTagInformationByNewTag(Feed feed, Tag tag) {
        if (Objects.isNull(feed.getTagId())) {
            attachFeedTagWithValidation(tag);
            feed.updateTag(tag);
        }else if (!feed.getTagName().equals(tag.name())) {
            detachFeedTagWithValidation(feed);
            attachFeedTagWithValidation(tag);
            feed.updateTag(tag);
        }
    }

    private FeedDetailResponse getFeedDetailResponse(
            Member viewer, Feed feed,
            int likeCount,
            boolean isLikeFeed,
            List<ImageElement> images) {
        Member feedWriter = feed.getMember();
        Tag tag = feed.getTagId() == null ? null : new Tag(feed.getTagId(), feed.getProductType(), feed.getTagName());

        if (Objects.isNull(viewer)) {
            return FeedMapper.supplyFeedDetailEntityOf(feed, tag, images, likeCount, false, false);
        } else if (isLikeFeed && isFeedWrittenByViewer(feedWriter.getId(), viewer.getId())) {
            return FeedMapper.supplyFeedDetailEntityOf(feed, tag, images, likeCount, true, true);
        } else if (isLikeFeed) {
            return FeedMapper.supplyFeedDetailEntityOf(feed, tag, images, likeCount, true, false);
        } else if (isFeedWrittenByViewer(feedWriter.getId(), viewer.getId())) {
            return FeedMapper.supplyFeedDetailEntityOf(feed, tag, images, likeCount, false, true);
        } else {
            return FeedMapper.supplyFeedDetailEntityOf(feed, tag, images, likeCount, false, false);
        }
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

    private boolean decreaseFeedLikeCount(Feed feed) {
        feedRedisUtil.decreaseLikeCount(feed);
        return false;
    }

    private boolean increaseFeedLikeCount(Feed feed) {
        feedRedisUtil.increaseLikeCount(feed);
        return true;
    }

    private Capsule getCapsule(Long tagId) {
        return capsuleRepository.findById(tagId)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));
    }

    private Coffee getCoffee(Long tagId) {
        return coffeeRepository.findById(tagId)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));
    }

    private void detachFeedTagWithValidation(Feed feed) {
        if (feed.getProductType() == ProductType.COFFEE_BEAN) {
            Coffee coffee = getCoffee(feed.getTagId());
            coffee.detachFeed();
        } else if (feed.getProductType() == ProductType.COFFEE_CAPSULE) {
            Capsule capsule = getCapsule(feed.getTagId());
            capsule.detachFeed();
        }
    }

    private void attachFeedTagWithValidation(Tag tag) {
        if (tag.category() == ProductType.COFFEE_BEAN) {
            Coffee coffee = getCoffee(tag.tagId());
            coffee.attachFeed();
        } else if (tag.category() == ProductType.COFFEE_CAPSULE) {
            Capsule capsule = getCapsule(tag.tagId());
            capsule.attachFeed();
        }
    }
}
