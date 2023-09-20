package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.domain.FeedLike;
import com.ssafy.coffeeing.modules.feed.dto.*;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.search.domain.TagType;
import com.ssafy.coffeeing.modules.search.domain.Tag;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Profile("test")
public class FeedTestDummy {

    public static UploadFeedRequest createUploadFeedRequestWithoutTag() {
        return new UploadFeedRequest(null, createImageElement(), "첫 번째 피드1");
    }

    public static UploadFeedRequest createUploadFeedRequestWithTag(Capsule capsule) {
        return new UploadFeedRequest(new Tag(capsule.getId(), TagType.CAPSULE, capsule.getCapsuleNameKr()),
                createImageElement(), "첫 번째 피드1");
    }

    public static List<ImageElement> createImageElement() {
        return List.of(new ImageElement("https://image1.webp"), new ImageElement("https://image2.webp"));
    }

    public static FeedsRequest createFeedsRequest(Long cursor, Integer size) {
        return new FeedsRequest(cursor, size);
    }

    public static MemberFeedsRequest createMemberFeedsRequest(Long memberId, Long cursor, Integer size) {
        return new MemberFeedsRequest(memberId, cursor, size);
    }

    public static Feed createFeed(Member member) {
        return Feed.builder()
                .member(member)
                .likeCount(0L)
                .content("testFeed")
                .imageUrl("[{\"imageUrl\":\"https://image1.webp\"},{\"imageUrl\":\"https://image2.webp\"}]")
                .build();
    }

    public static FeedLike createFeedLike(Feed feed, Member member) {
        return FeedLike.builder()
                .feed(feed)
                .member(member)
                .build();
    }

    public static List<Feed> createFeeds(Member member) {
        List<Feed> feeds = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            feeds.add(Feed.builder()
                    .member(member)
                    .likeCount(0L)
                    .content(i + "contents")
                    .imageUrl("[{\"imageUrl\":\"https://image1.webp\"},{\"imageUrl\":\"https://image2.webp\"}]")
                    .build());
        }
        return feeds;
    }

    public static UpdateFeedRequest createUpdateFeedRequestWithoutTag() {
        return new UpdateFeedRequest("testUpdateContent", null);
    }

    public static UpdateFeedRequest createUpdateFeedRequestWithTag(Coffee coffee) {
        return new UpdateFeedRequest("testUpdateContent",
                new Tag(coffee.getId(), TagType.BEAN, coffee.getCoffeeNameKr()));
    }
}
