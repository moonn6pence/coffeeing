package com.ssafy.coffeeing.modules.product.dto;

public record ProductReviewElement(
        Long id,
        Double score,
        String content,
        String nickname
) {
}
