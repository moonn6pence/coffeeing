package com.ssafy.coffeeing.modules.product.dto;

import java.util.List;

public record SimilarCoffeeResponse(
        List<SimilarCoffeeElement> products
) {
}
