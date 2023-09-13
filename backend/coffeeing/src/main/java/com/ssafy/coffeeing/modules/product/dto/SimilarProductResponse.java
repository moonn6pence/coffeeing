package com.ssafy.coffeeing.modules.product.dto;

import java.util.List;

public record SimilarProductResponse(
        Boolean isCapsule,
        List<SimpleProductElement> products
) {
}
