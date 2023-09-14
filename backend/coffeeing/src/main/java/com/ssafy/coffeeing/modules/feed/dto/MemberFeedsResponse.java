package com.ssafy.coffeeing.modules.feed.dto;

import java.util.List;

public record MemberFeedsResponse(
        List<FeedElement> feeds
) {
}
