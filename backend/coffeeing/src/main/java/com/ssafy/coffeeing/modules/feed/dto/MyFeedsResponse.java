package com.ssafy.coffeeing.modules.feed.dto;

import java.util.List;

public record MyFeedsResponse(
        List<MyFeedElement> feeds
) {
}
