package com.ssafy.coffeeing.modules.recommend.service;

import com.ssafy.coffeeing.modules.recommend.dto.RecommendResponse;
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile({"test", "dev"})
@Service
public class MockRecommendService implements RecommendService{

    private static final List<Long> mockCapsuleIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L);
    private static final List<Long> mockCoffeeIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L);

    @Override
    public RecommendResponse pickByPreference(Integer count, PreferenceRequest preferenceRequest) {

        return new RecommendResponse(preferenceRequest.isCapsule().equals(Boolean.TRUE)
                ? mockCapsuleIds.subList(0, count)
                : mockCoffeeIds.subList(0, count));
    }

    @Override
    public RecommendResponse pickBySimilarity(Integer count, Boolean isCapsule, Long id) {

        return new RecommendResponse(isCapsule.equals(Boolean.TRUE)
                ? mockCapsuleIds.subList(0, count)
                : mockCoffeeIds.subList(0, count));
    }

    @Override
    public RecommendResponse pickByCriteria(Integer count, Boolean isCapsule, String criteria, String attribute) {
        return new RecommendResponse(isCapsule.equals(Boolean.TRUE)
                ? mockCapsuleIds.subList(0, count)
                : mockCoffeeIds.subList(0, count));
    }
}
