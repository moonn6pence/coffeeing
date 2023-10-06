package com.ssafy.coffeeing.modules.feed.dto;

import java.util.List;

public record ProfileFeedsResponse(
        List<FeedElement> feeds,
        Boolean hasNext,
        Long nextCursor
) {
}
