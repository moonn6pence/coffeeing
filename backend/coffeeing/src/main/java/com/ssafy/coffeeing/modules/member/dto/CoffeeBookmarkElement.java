package com.ssafy.coffeeing.modules.member.dto;

public record CoffeeBookmarkElement(
        Long id,
        String regionKr,
        String nameKr,
        String imageUrl

) implements BookmarkProductElement {
}
