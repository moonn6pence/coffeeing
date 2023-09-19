package com.ssafy.coffeeing.modules.recommend.service;

import com.ssafy.coffeeing.modules.recommend.dto.RecommendResponse;
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("dev")
@Service
public class MockRecommendService implements RecommendService{
    @Override
    public RecommendResponse getRecommendationsByParameter(PreferenceRequest preferenceRequest) {

        List<Long> mock = List.of(1L,2L,3L,4L,5L);

        return new RecommendResponse(mock);
    }

    @Override
    public RecommendResponse getSimilarProduct(Boolean isCapsule, Long id) {

        List<Long> mock = List.of(1L,2L,3L,4L,5L);

        return new RecommendResponse(mock);
    }
}
