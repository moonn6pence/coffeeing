package com.ssafy.coffeeing.modules.member.dto;

import java.util.List;

public record BookmarkResponse(
        Integer page,
        Integer totalCount,
        List<BookmarkProductElement> bookmarkedElements,
        Boolean isCapsule
) {


}
