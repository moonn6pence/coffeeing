package com.ssafy.coffeeing.modules.product.dto;

public record CoffeeResponse(
        Long id,
        String regionKr,
        String nameKr,
        String imageUrl,
        String aroma,
        Double roast,
        Double acidity,
        Double body,
        String description,
        Double averageScore,
        Boolean isBookmarked,
        Boolean isReviewed,
        ProductReviewElement memberReview
) {
}
