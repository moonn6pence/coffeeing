package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.domain.FeedLike;
import com.ssafy.coffeeing.modules.feed.dto.ImageElement;
import com.ssafy.coffeeing.modules.feed.dto.UpdateFeedRequest;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedRequest;
import com.ssafy.coffeeing.modules.member.domain.Member;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("test")
public class FeedTestDummy {

    public static UploadFeedRequest createUploadFeedRequest() {
        return new UploadFeedRequest(createImageElement(), "첫 번째 피드1");
    }

    public static List<ImageElement> createImageElement() {
        return List.of(new ImageElement("https://image1.webp"), new ImageElement("https://image2.webp"));
    }

    public static Feed createFeed(Member member) {
        return Feed.builder()
                .member(member)
                .likeCount(0L)
                .content("testFeed")
                .imageUrl("testFeedImageUrls")
                .build();
    }

    public static FeedLike createFeedLike(Feed feed, Member member) {
        return FeedLike.builder()
                .feed(feed)
                .member(member)
                .build();
    }

    public static UpdateFeedRequest createUpdateFeedRequest() {
        return new UpdateFeedRequest("testUpdateContent");
    }
}
