package com.ssafy.coffeeing.modules.product.dto;

import java.util.List;

public record CapsuleReviewResponse(
        Boolean isReviewedByUser,
        CapsuleReviewElement userReview,
        List<CapsuleReviewElement> reviews
) {
}
