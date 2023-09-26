package com.ssafy.coffeeing.modules.survey.service;

import com.ssafy.coffeeing.modules.event.eventer.ExperienceEvent;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.dto.SimpleProductElement;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.recommend.dto.RecommendResponse;
import com.ssafy.coffeeing.modules.recommend.service.RecommendService;
import com.ssafy.coffeeing.modules.survey.domain.Preference;
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import com.ssafy.coffeeing.modules.survey.dto.SurveyResponse;
import com.ssafy.coffeeing.modules.survey.mapper.SurveyMapper;
import com.ssafy.coffeeing.modules.survey.repository.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SecurityContextUtils securityContextUtils;
    private final PreferenceRepository preferenceRepository;
    private final RecommendService recommendService;
    private final CapsuleRepository capsuleRepository;
    private final CoffeeRepository coffeeRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private static final Integer SURVEY_EXPERIENCE = 15;

    @Transactional
    public SurveyResponse recommendBySurvey(PreferenceRequest preferenceRequest) {

        Member member = securityContextUtils.getMemberIdByTokenOptionalRequest();

        RecommendResponse recommendResponse = recommendService.pickByPreference(preferenceRequest);

        List<SimpleProductElement> products = preferenceRequest.isCapsule() ?
                capsuleRepository.findAllById(recommendResponse.results())
                        .stream()
                        .map(ProductMapper::supplySimpleProductElementFrom)
                        .toList() :
                coffeeRepository.findAllById(recommendResponse.results())
                        .stream()
                        .map(ProductMapper::supplySimpleProductElementFrom)
                        .toList();

        if (member == null) {
            return SurveyMapper.supplySurveyResponseOf(products, preferenceRequest);
        }

        applicationEventPublisher.publishEvent(new ExperienceEvent(SURVEY_EXPERIENCE, member.getId()));

        return SurveyMapper.supplySurveyResponseOf(products, preferenceRequest, member);
    }

    @Transactional
    public void savePreference(PreferenceRequest preferenceRequest) {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        Preference preference = preferenceRepository.findByMemberId(member.getId());

        if (preference != null) {
            preference.update(preferenceRequest);
            return;
        }

        preference = SurveyMapper.supplyPreferenceOf(preferenceRequest, member.getId());
        preferenceRepository.save(preference);
    }
}
