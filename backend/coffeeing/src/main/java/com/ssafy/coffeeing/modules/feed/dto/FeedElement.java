package com.ssafy.coffeeing.modules.feed.dto;

import java.util.List;

public record FeedElement(
        Long feedId,
        List<ImageElement> images
) {
}
