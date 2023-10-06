package com.ssafy.coffeeing.modules.recommend.service;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.SurveyErrorInfo;
import com.ssafy.coffeeing.modules.recommend.dto.RecommendResponse;
import com.ssafy.coffeeing.modules.recommend.property.RecSysProperty;
import com.ssafy.coffeeing.modules.recommend.util.RecommendCacheUtil;
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@Profile({"prod","dev"})
@RequiredArgsConstructor
@Service
public class DefaultRecommendService implements RecommendService {

    private final RecSysProperty recSysProperty;
    private final RestTemplate restTemplate;
    private final RecommendCacheUtil recommendCacheUtil;
    private static final String PARAM_COUNT = "count";
    private static final String PARAM_IS_CAPSULE = "isCapsule";

    @Override
    public RecommendResponse pickByPreference(Integer count, PreferenceRequest preferenceRequest) {

        URI uri = UriComponentsBuilder
                .fromUriString(recSysProperty.getPickByPreferenceUrl())
                .queryParam(PARAM_COUNT, count)
                .queryParam(PARAM_IS_CAPSULE, preferenceRequest.isCapsule())
                .queryParam("machineType", Optional.ofNullable(preferenceRequest.machineType()).orElse(1))
                .queryParam("roast", preferenceRequest.roast())
                .queryParam("acidity", preferenceRequest.acidity())
                .queryParam("body", preferenceRequest.body())
                .queryParam("flavorNote", preferenceRequest.flavorNote())
                .encode()
                .build()
                .toUri();

        return getRecSysResponse(count, uri);
    }

    @Override
    public RecommendResponse pickBySimilarity(Integer count, Boolean isCapsule, Long id) {

        URI uri = UriComponentsBuilder
                .fromUriString(recSysProperty.getPickBySimilarityUrl())
                .queryParam(PARAM_COUNT, count)
                .queryParam(PARAM_IS_CAPSULE, isCapsule)
                .queryParam("id", id)
                .encode()
                .build()
                .toUri();

        return getRecSysResponse(count, uri);
    }

    @Override
    public RecommendResponse pickByCriteria(Integer count, Boolean isCapsule, String criteria, String attribute) {

        URI uri = UriComponentsBuilder
                .fromUriString(recSysProperty.getPickByCriteriaUrl())
                .queryParam(PARAM_COUNT, count)
                .queryParam(PARAM_IS_CAPSULE, isCapsule)
                .queryParam("criteria", criteria)
                .queryParam("attribute", attribute)
                .encode()
                .build()
                .toUri();

        return getRecSysResponse(count, uri);
    }

    private RecommendResponse getRecSysResponse(Integer count, URI uri) {

        try {
            return new RecommendResponse(recommendCacheUtil.getAll(uri.toString(), count));
        } catch (BusinessException e) {
            recommendCacheUtil.pushAll(uri.toString(),
                    Objects.requireNonNull(restTemplate.getForObject(uri, RecommendResponse.class))
                            .results().stream().map(String::valueOf).toList()
            );

            return new RecommendResponse(recommendCacheUtil.getAll(uri.toString(), count));
        } catch (HttpClientErrorException e) {
            throw new BusinessException(SurveyErrorInfo.BAD_API_SERVER_REQUEST);
        } catch (HttpServerErrorException e) {
            throw new BusinessException(SurveyErrorInfo.EXTERNAL_SERVER_ERROR);
        }
    }
}
