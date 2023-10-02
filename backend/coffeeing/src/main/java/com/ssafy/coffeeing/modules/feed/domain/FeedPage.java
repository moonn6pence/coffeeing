package com.ssafy.coffeeing.modules.feed.domain;

import com.ssafy.coffeeing.modules.feed.dto.FeedPageElement;
import com.ssafy.coffeeing.modules.feed.util.FeedUtil;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.search.domain.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FeedPage {
    public List<FeedPageElement> feedPageElements;

    public FeedPage(List<Feed> feeds, List<FeedLike> feedLikes, Member viewer, FeedUtil feedUtil) {
        feedPageElements = new ArrayList<>();
        makeFeedImageUrlsToObject(feeds, feedUtil);
        changeIsMineStatus(viewer);
        changeIsLikeStatus(feedLikes);
    }

    private void makeFeedImageUrlsToObject(List<Feed> feeds, FeedUtil feedUtil) {
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
                    member.getProfileImage()));
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

    private void changeIsLikeStatus(List<FeedLike> feedLikes) {
        for (FeedLike feedLike : feedLikes) {
            for (FeedPageElement feedPageElement : feedPageElements) {
                Feed feed = feedLike.getFeed();
                if (Objects.equals(feed.getId(), feedPageElement.getFeedId())) {
                    feedPageElement.updateIsLikeStatus();
                    break;
                }
            }
        }
    }
}
