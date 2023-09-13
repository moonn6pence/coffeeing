package com.ssafy.coffeeing.modules.product.dto;

import java.util.List;

public record CoffeeReviewResponse(
        Boolean isReviewedByUser,
        CoffeeReviewElement userReview,
        List<CoffeeReviewElement> reviews
) {
}
