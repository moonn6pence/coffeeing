package com.ssafy.coffeeing.modules.product.dto;

public record CapsuleReviewElement(
        Long id,
        Double score,
        String content,
        String nickname
) {
}
