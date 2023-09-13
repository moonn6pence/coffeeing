package com.ssafy.coffeeing.modules.product.dto;

public record CoffeeReviewElement(
        Long id,
        Double score,
        String content,
        String nickname
) {
}
