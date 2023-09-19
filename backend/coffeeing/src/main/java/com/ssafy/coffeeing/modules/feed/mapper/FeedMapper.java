package com.ssafy.coffeeing.modules.feed.mapper;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.dto.*;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.tag.domain.Tag;
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

    public static Feed supplyFeedEntityOf(Member member, String content, String imageUrl, Tag tag) {
        return Feed.builder()
                .member(member)
                .content(content)
                .imageUrl(imageUrl)
                .likeCount(0L)
                .tagId(tag.tagId())
                .tagType(tag.category())
                .tagName(tag.name())
                .build();
    }

    public static ProfileFeedsResponse supplyFeedEntityOf(List<FeedElement> feeds, Boolean hasNext, Long nextCursor) {
        return new ProfileFeedsResponse(feeds, hasNext, nextCursor);
    }

    public static FeedDetailResponse supplyFeedDetailEntityOf(
            Feed feed,
            Tag tag,
            List<ImageElement> images,
            Boolean isLike,
            Boolean isMine) {
        return new FeedDetailResponse(feed.getId(), images, feed.getContent(), tag, feed.getMember().getId(),
                feed.getLikeCount(), feed.getMember().getNickname(), feed.getMember().getProfileImage(), isLike, isMine);
    }

    public static FeedPageResponse supplyFeedPageEntityOf(List<FeedPageElement> images, Boolean hasNext, Long nextCursor) {
        return new FeedPageResponse(images, hasNext, nextCursor);
    }
}
