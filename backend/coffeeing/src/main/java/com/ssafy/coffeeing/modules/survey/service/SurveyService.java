package com.ssafy.coffeeing.modules.survey.service;

import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.survey.domain.Preference;
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import com.ssafy.coffeeing.modules.survey.dto.SurveyResponse;
import com.ssafy.coffeeing.modules.survey.mapper.SurveyMapper;
import com.ssafy.coffeeing.modules.survey.repository.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SecurityContextUtils securityContextUtils;

    private final PreferenceRepository preferenceRepository;

    @Transactional
    public SurveyResponse recommendBySurvey(PreferenceRequest preferenceRequest) {

        // http로 fastApi에 추천 요청 -> RecommendService.recommend() -> @Profile("test") TestRecommendService.recommend()

        // 결과 받아서 db에서 추가 정보 찾아서 DTO로 감싸고 리턴

        return null;
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
