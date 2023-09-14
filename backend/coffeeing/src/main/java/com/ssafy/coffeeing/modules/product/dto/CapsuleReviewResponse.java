package com.ssafy.coffeeing.modules.product.dto;

import java.util.List;

public record CapsuleReviewResponse(
        Integer page,
        Integer totalCount,
        List<CapsuleReviewElement> reviews
) {
}
