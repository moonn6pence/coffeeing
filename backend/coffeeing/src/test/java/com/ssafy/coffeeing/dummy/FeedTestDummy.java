package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.dto.ImageElement;
import com.ssafy.coffeeing.modules.feed.dto.UpdateFeedRequest;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedRequest;
import com.ssafy.coffeeing.modules.member.domain.Member;

import java.util.List;

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

    public static UpdateFeedRequest createUpdateFeedRequest() {
        return new UpdateFeedRequest("testUpdateContent");
    }
}
