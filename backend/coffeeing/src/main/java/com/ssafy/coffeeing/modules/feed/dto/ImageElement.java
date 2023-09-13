package com.ssafy.coffeeing.modules.feed.dto;

import com.ssafy.coffeeing.modules.feed.validator.FeedImageUrl;

public record ImageElement(
        @FeedImageUrl
        String imageUrl
) { }
