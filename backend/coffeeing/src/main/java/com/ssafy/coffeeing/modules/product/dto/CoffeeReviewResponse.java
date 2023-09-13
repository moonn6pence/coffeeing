package com.ssafy.coffeeing.modules.product.dto;

import java.util.List;

public record CoffeeReviewResponse(
        Boolean isReviewed,
        CoffeeReviewElement memberReview,
        List<CoffeeReviewElement> reviews
) {
}
