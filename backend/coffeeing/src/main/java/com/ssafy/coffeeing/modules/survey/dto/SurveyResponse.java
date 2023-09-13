package com.ssafy.coffeeing.modules.survey.dto;

import com.ssafy.coffeeing.modules.product.dto.SimilarProductResponse;

public record SurveyResponse(
        SimilarProductResponse recommendation,
        String nickname,
        String imageUrl,
        Double roast,
        Double acidity,
        Double body
) {
}
