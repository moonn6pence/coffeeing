package com.ssafy.coffeeing.modules.feed.dto;

import java.util.List;

public record FeedPageResponse(
        List<FeedPageElement> feeds,
        Boolean hasNext,
        Long nextCursor
) {
}
