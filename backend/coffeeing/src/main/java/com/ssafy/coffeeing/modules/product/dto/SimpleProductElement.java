package com.ssafy.coffeeing.modules.product.dto;

public record SimpleProductElement(
        Long id,
        String brand,
        String name,
        String imageUrl
) {
}
