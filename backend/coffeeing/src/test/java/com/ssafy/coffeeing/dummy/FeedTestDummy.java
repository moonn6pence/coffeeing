package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.feed.dto.ImageElement;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedRequest;

import java.util.List;

public class FeedTestDummy {

    public static UploadFeedRequest createUploadFeedRequest() {
        return new UploadFeedRequest(createImageElement(), "첫 번째 피드1");
    }

    public static List<ImageElement> createImageElement() {
        return List.of(new ImageElement("https://image1.webp"), new ImageElement("https://image2.webp"));
    }
}
