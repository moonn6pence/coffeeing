package com.ssafy.coffeeing.modules.recommend.service;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.SurveyErrorInfo;
import com.ssafy.coffeeing.modules.recommend.dto.RecommendResponse;
import com.ssafy.coffeeing.modules.recommend.property.RecSysProperty;
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Profile({"prod"})
@RequiredArgsConstructor
@Service
public class DefaultRecommendService implements RecommendService {

    private final RecSysProperty recSysProperty;

    private final RestTemplate restTemplate;

    @Override
    public RecommendResponse pickByPreference(Integer count, PreferenceRequest preferenceRequest) {

        URI uri = UriComponentsBuilder
                .fromUriString(recSysProperty.getPickByPreferenceUrl())
                .queryParam("count", count)
                .queryParam("isCapsule", preferenceRequest.isCapsule())
                .queryParamIfPresent("machineType", Optional.of(preferenceRequest.machineType()))
                .queryParam("roast", preferenceRequest.roast())
                .queryParam("acidity", preferenceRequest.acidity())
                .queryParam("body", preferenceRequest.body())
                .queryParam("flavorNote", preferenceRequest.flavorNote())
                .encode()
                .build()
                .toUri();

        try {
            return restTemplate.getForObject(uri, RecommendResponse.class);
        } catch (HttpClientErrorException e) {
            throw new BusinessException(SurveyErrorInfo.BAD_API_SERVER_REQUEST);
        } catch (HttpServerErrorException e) {
            throw new BusinessException(SurveyErrorInfo.EXTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RecommendResponse pickBySimilarity(Integer count, Boolean isCapsule, Long id) {

        URI uri = UriComponentsBuilder
                .fromUriString(recSysProperty.getPickBySimilarityUrl())
                .queryParam("count", count)
                .queryParam("isCapsule", isCapsule)
                .queryParam("id", id)
                .encode()
                .build()
                .toUri();

        try {
            return restTemplate.getForObject(uri, RecommendResponse.class);
        } catch (HttpClientErrorException e) {
            throw new BusinessException(SurveyErrorInfo.BAD_API_SERVER_REQUEST);
        } catch (HttpServerErrorException e) {
            throw new BusinessException(SurveyErrorInfo.EXTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RecommendResponse pickByCriteria(Integer count, Boolean isCapsule, String criteria, String attribute) {

        URI uri = UriComponentsBuilder
                .fromUriString(recSysProperty.getPickByCriteriaUrl())
                .queryParam("count", count)
                .queryParam("isCapsule", isCapsule)
                .queryParam("criteria", criteria)
                .queryParam("attribute", attribute)
                .encode()
                .build()
                .toUri();

        try {
            return restTemplate.getForObject(uri, RecommendResponse.class);
        } catch (HttpClientErrorException e) {
            throw new BusinessException(SurveyErrorInfo.BAD_API_SERVER_REQUEST);
        } catch (HttpServerErrorException e) {
            throw new BusinessException(SurveyErrorInfo.EXTERNAL_SERVER_ERROR);
        }
    }
}
