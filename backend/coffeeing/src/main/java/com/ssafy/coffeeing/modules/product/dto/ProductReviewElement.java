package com.ssafy.coffeeing.modules.product.dto;

public record ProductReviewElement(
        Long reviewId,
        Integer score,
        String content,
        Long memberId,
        String profileImageUrl,
        String nickname
) {
}
