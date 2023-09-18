package com.ssafy.coffeeing.modules.member.dto;

import java.util.List;

public record BookmarkedResponse(
        Integer page,
        Integer totalCount,
        List<BookmarkedElement> bookmarkedElements
) {


}
