package com.ssafy.coffeeing.modules.feed.domain;

import com.ssafy.coffeeing.modules.feed.dto.FeedPageElement;
import com.ssafy.coffeeing.modules.feed.util.FeedRedisUtil;
import com.ssafy.coffeeing.modules.feed.util.FeedUtil;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.search.domain.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FeedPage {
    public List<FeedPageElement> feedPageElements;

    public FeedPage(List<Feed> feeds, FeedRedisUtil feedRedisUtil, Member viewer, FeedUtil feedUtil) {
        feedPageElements = new ArrayList<>();
        makeFeedImageUrlsToObject(feeds, viewer, feedRedisUtil, feedUtil);
        changeIsMineStatus(viewer);
    }

    private void makeFeedImageUrlsToObject(List<Feed> feeds, Member viewer, FeedRedisUtil feedRedisUtil, FeedUtil feedUtil) {
        feeds.forEach(feed -> {
            Member member = feed.getMember();
            Tag tag = feed.getTagId() == null ? null : new Tag(feed.getTagId(), feed.getProductType(), feed.getTagName());

            feedPageElements.add(new FeedPageElement(feed.getId(),
                    feedUtil.makeJsonStringToImageElement(feed.getImageUrl()),
                    feed.getContent(),
                    tag,
                    member.getId(),
                    feed.getLikeCount(),
                    member.getNickname(),
                    member.getProfileImage(),
                    feedRedisUtil.isLikedFeedInRedis(feed, viewer)));
        });
    }

    private void changeIsMineStatus(Member viewer) {
        Long viewerId = (viewer != null) ? viewer.getId() : null;

        feedPageElements.forEach(feedPageElement -> {
            if (Objects.equals(feedPageElement.getRegisterId(), viewerId)) {
                feedPageElement.updateIsMineStatus();
            }
        });
    }
}
