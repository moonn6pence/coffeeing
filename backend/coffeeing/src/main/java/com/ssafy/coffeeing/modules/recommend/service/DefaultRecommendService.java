package com.ssafy.coffeeing.modules.recommend.service;

import com.ssafy.coffeeing.modules.recommend.dto.RecommendResponse;
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Profile({"prod"})
@RequiredArgsConstructor
@Service
public class DefaultRecommendService implements RecommendService {

    private static final String baseUrl = "http://localhost:80/rec";
    private static final String recByParamUrl = baseUrl.concat("/collab");
    private static final String recByProductUrl = baseUrl.concat("/content");

    private final RestTemplate restTemplate;

    @Override
    public RecommendResponse getRecommendationsByParameter(PreferenceRequest preferenceRequest) {

        URI uri = UriComponentsBuilder
                .fromUriString(recByParamUrl)
                .queryParam("isCapsule", preferenceRequest.isCapsule())
                .queryParamIfPresent("machineType", Optional.of(preferenceRequest.machineType()))
                .queryParam("roast", preferenceRequest.roast())
                .queryParam("acidity", preferenceRequest.acidity())
                .queryParam("body", preferenceRequest.body())
                .queryParam("flavorNote", preferenceRequest.flavorNote())
                .encode()
                .build()
                .toUri();

        return restTemplate.getForObject(uri, RecommendResponse.class);
    }

    @Override
    public RecommendResponse getSimilarProduct(Boolean isCapsule, Long id) {

        URI uri = UriComponentsBuilder
                .fromUriString(recByProductUrl)
                .queryParam("isCapsule", isCapsule)
                .queryParam("id", id)
                .encode()
                .build()
                .toUri();

        return restTemplate.getForObject(uri, RecommendResponse.class);
    }
}
