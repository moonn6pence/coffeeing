package com.ssafy.coffeeing.modules.member.dto;

import com.ssafy.coffeeing.modules.product.dto.SimpleProductElement;

import java.util.List;

public record BookmarkResponse(
        Integer page,
        Integer totalCount,
        List<SimpleProductElement> bookmarkedElements,
        Boolean isCapsule
) {


}
