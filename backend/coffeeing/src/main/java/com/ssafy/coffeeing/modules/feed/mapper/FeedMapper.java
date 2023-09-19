package com.ssafy.coffeeing.modules.feed.mapper;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.dto.*;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.tag.dto.TagElement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeedMapper {

    public static UploadFeedResponse supplyFeedResponseBy(Feed feed) {
        return new UploadFeedResponse(feed.getId());
    }

    public static Feed supplyFeedEntityOf(Member member, String content, String imageUrl) {
        return Feed.builder()
                .member(member)
                .content(content)
                .imageUrl(imageUrl)
                .likeCount(0L)
                .build();
    }

    public static Feed supplyFeedEntityOf(Member member, String content, String imageUrl, TagElement tagElement) {
        return Feed.builder()
                .member(member)
                .content(content)
                .imageUrl(imageUrl)
                .likeCount(0L)
                .tagId(tagElement.tagId())
                .tagType(tagElement.category())
                .tagName(tagElement.name())
                .build();
    }

    public static ProfileFeedsResponse supplyFeedEntityOf(List<FeedElement> feeds, Boolean hasNext, Long nextCursor) {
        return new ProfileFeedsResponse(feeds, hasNext, nextCursor);
    }

    public static FeedDetailResponse supplyFeedDetailEntityOf(Feed feed, List<ImageElement> images, Boolean isLike, Boolean isMine) {
        return new FeedDetailResponse(feed.getId(), images, feed.getContent(), feed.getMember().getId(),
                feed.getLikeCount(), feed.getMember().getNickname(), feed.getMember().getProfileImage(), isLike, isMine);
    }

    public static FeedPageResponse supplyFeedPageEntityOf(List<FeedPageElement> images, Boolean hasNext, Long nextCursor) {
        return new FeedPageResponse(images, hasNext, nextCursor);
    }
}
