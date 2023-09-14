package com.ssafy.coffeeing.modules.product.dto;

public record CapsuleResponse(
        Long id,
        String brand,
        String name,
        String imageUrl,
        String aroma,
        Double roast,
        Double acidity,
        Double body,
        String description,
        Double averageScore,
        Boolean isBookmarked,
        Boolean isReviewed,
        CapsuleReviewElement memberReview
) {
}
