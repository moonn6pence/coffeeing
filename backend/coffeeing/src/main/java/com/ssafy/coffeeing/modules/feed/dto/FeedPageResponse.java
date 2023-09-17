package com.ssafy.coffeeing.modules.feed.dto;

import java.util.List;

public record FeedPageResponse(
        List<FeedDetailResponse> feeds,
        Boolean hasNext,
        Long nextCursor
) {
}
