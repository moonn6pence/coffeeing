package com.ssafy.coffeeing.modules.product.dto;

public record ProductReviewElement(
        Long reviewId,
        Double score,
        String content,
        Long memberId,
        String profileImageUrl,
        String nickname
) {
}
