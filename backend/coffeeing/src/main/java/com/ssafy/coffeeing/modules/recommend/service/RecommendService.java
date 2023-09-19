package com.ssafy.coffeeing.modules.recommend.service;

import com.ssafy.coffeeing.modules.recommend.dto.RecommendResponse;
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;

public interface RecommendService {

    RecommendResponse getRecommendationsByParameter(PreferenceRequest preferenceRequest);

    RecommendResponse getSimilarProduct(Boolean isCapsule, Long id);

}
