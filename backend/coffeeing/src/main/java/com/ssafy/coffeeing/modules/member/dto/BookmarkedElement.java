package com.ssafy.coffeeing.modules.member.dto;

public record BookmarkedElement(
    Long elementId,
    String elementName,
    String elementRegionOrBrand,
    String elementImageUrl
) {
}
