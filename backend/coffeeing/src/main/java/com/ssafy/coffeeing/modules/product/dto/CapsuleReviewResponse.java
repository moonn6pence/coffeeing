package com.ssafy.coffeeing.modules.product.dto;

import java.util.List;

public record CapsuleReviewResponse(
        Boolean isReviewed,
        CapsuleReviewElement memberReview,
        List<CapsuleReviewElement> reviews
) {
}
