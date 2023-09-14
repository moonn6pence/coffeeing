package com.ssafy.coffeeing.modules.product.dto;

public record CoffeeResponse(
        Long id,
        String name,
        String imageUrl,
        String aroma,
        Double roast,
        Double acidity,
        Double body,
        String description,
        Double averageScore,
        Boolean isBookmarked
) {
}
