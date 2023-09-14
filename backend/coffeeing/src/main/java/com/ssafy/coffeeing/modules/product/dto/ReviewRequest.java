package com.ssafy.coffeeing.modules.product.dto;

public record ReviewRequest(
        Double score,
        String content
) {
}
