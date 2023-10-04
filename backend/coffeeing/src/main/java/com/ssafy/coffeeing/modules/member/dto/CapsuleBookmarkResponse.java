package com.ssafy.coffeeing.modules.member.dto;

import java.util.List;

public record CapsuleBookmarkResponse(
        Integer page,
        Integer totalCount,
        List<CapsuleBookmarkElement> bookmarkedElements,
        Boolean isCapsule
) {
}
