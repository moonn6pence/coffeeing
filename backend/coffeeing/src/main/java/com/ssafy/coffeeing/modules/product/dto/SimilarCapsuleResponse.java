package com.ssafy.coffeeing.modules.product.dto;

import java.util.List;

public record SimilarCapsuleResponse(
        List<SimilarCapsuleElement> products
) {
}
