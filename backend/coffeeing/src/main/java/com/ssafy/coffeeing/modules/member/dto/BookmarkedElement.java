package com.ssafy.coffeeing.modules.member.dto;

public record BookmarkedElement(
    Long elementId,
    String elementName,
    String elementRegionOrCompany,
    String elementImageUrl,
    Long elementBookmarkId
) {
}
