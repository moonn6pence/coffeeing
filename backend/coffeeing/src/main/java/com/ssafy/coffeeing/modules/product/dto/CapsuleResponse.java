package com.ssafy.coffeeing.modules.product.dto;

public record CapsuleResponse(
        Long id,
        String brandKr,
        String name,
        String aroma,
        Double roast,
        Double acidity,
        Double body,
        String description,
        Double averageScore
) {
}
