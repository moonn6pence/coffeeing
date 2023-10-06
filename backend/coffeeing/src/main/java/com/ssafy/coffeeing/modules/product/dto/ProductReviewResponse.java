package com.ssafy.coffeeing.modules.product.dto;

import java.util.List;

public record ProductReviewResponse(
        Integer page,
        Integer totalCount,
        List<ProductReviewElement> reviews
) {
}
