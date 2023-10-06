package com.ssafy.coffeeing.modules.member.dto;

import java.util.List;

public record CoffeeBookmarkResponse(
        Integer page,
        Integer totalCount,
        List<CoffeeBookmarkElement> bookmarkedElements,
        Boolean isCapsule
) {
}
