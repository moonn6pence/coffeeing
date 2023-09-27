package com.ssafy.coffeeing.modules.curation.service;

import com.ssafy.coffeeing.modules.curation.domain.CurationType;
import com.ssafy.coffeeing.modules.curation.dto.CurationElement;
import com.ssafy.coffeeing.modules.curation.dto.CurationResponse;
import com.ssafy.coffeeing.modules.curation.mapper.CurationMapper;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Age;
import com.ssafy.coffeeing.modules.member.domain.Gender;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.dto.PreferenceAverage;
import com.ssafy.coffeeing.modules.member.repository.MemberQueryRepository;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleReviewQueryRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeReviewQueryRepository;
import com.ssafy.coffeeing.modules.recommend.dto.RecommendResponse;
import com.ssafy.coffeeing.modules.recommend.service.RecommendService;
import com.ssafy.coffeeing.modules.survey.domain.Preference;
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import com.ssafy.coffeeing.modules.survey.mapper.SurveyMapper;
import com.ssafy.coffeeing.modules.survey.repository.PreferenceRepository;
import com.ssafy.coffeeing.modules.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CurationService {

    private final RecommendService recommendService;
    private final RandomUtil randomUtil;
    private final SecurityContextUtils securityContextUtils;
    private final CapsuleRepository capsuleRepository;
    private final CapsuleReviewQueryRepository capsuleReviewQueryRepository;
    private final CoffeeRepository coffeeRepository;
    private final CoffeeReviewQueryRepository coffeeReviewQueryRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final PreferenceRepository preferenceRepository;

    private static final String prefix = "당신이 좋아하는 ";

    @Transactional(readOnly = true)
    public CurationResponse getOpenCuration(Boolean isCapsule) {

        List<CurationElement> curations = new ArrayList<>();

        // 인기도 큐레이션
        curations.add(findByPopularity(isCapsule.equals(Boolean.TRUE)
                ? CurationType.CAPSULE_POPULAR
                : CurationType.COFFEE_POPULAR));

        // 지표별 유사제품군 큐레이션
        CurationType randomCuration = randomUtil.getRandomCharacteristicCuration(isCapsule);

        if (randomCuration.equals(CurationType.CAPSULE_FLAVOR) || randomCuration.equals(CurationType.COFFEE_FLAVOR)) {
            curations.add(findByFlavorNote(randomCuration, randomUtil.getRandomFlavor()));
        } else {
            curations.add(findByCharacteristic(randomCuration));
        }

        return CurationMapper.supplyCurationResponseFrom(curations);
    }

    @Transactional(readOnly = true)
    public CurationResponse getCustomCuration(Boolean isCapsule) {

        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        List<CurationElement> curations = new ArrayList<>();

        CurationType randomCuration = randomUtil.getRandomKeywordCuration(isCapsule);

        curations.add(findByMemberPreference(isCapsule.equals(Boolean.TRUE)
                ? CurationType.CAPSULE_PREFERENCE
                : CurationType.COFFEE_PREFERENCE, member));

        if (isCapsule.equals(Boolean.TRUE)) {
            Capsule capsule = capsuleReviewQueryRepository.findRandomHighScoredCapsule(member);

            if (capsule != null && randomCuration.equals(CurationType.CAPSULE_LIKED_PRODUCT)) {
                // 고평가 제품과 유사한 제품 큐레이션
                curations.add(findByMemberLikedProduct(randomCuration, capsule));
            } else {
                // 나이+성별 그룹의 취향 평균으로 큐레이션
                curations.add(findByAgeAndGender(CurationType.CAPSULE_AGE_GENDER, member.getAge(), member.getGender()));
            }
        } else {
            Coffee coffee = coffeeReviewQueryRepository.findRandomHighScoredCoffee(member);

            if (coffee != null && randomCuration.equals(CurationType.COFFEE_LIKED_PRODUCT)) {
                curations.add(findByMemberLikedProduct(randomCuration, coffee));
            } else {
                curations.add(findByAgeAndGender(CurationType.COFFEE_AGE_GENDER, member.getAge(), member.getGender()));
            }
        }

        return CurationMapper.supplyCurationResponseFrom(curations);
    }

    private CurationElement findByPopularity(CurationType curation) {

        if (curation.getIsCapsule().equals(Boolean.TRUE)) {
            return CurationMapper.supplyCapsuleCurationElementOf(true, curation.getTitle(),
                    capsuleRepository.findTop10CapsulesByOrderByPopularityDesc());
        }

        return CurationMapper.supplyCoffeeCurationElementOf(false, curation.getTitle(),
                coffeeRepository.findTop10CoffeesByOrderByPopularityDesc());
    }

    private CurationElement findByCharacteristic(CurationType curation) {

        RecommendResponse recommendResponse = recommendService.pickByCriteria(
                curation.getIsCapsule(),
                curation.getCriteria(),
                curation.getAttribute()
        );

        if (curation.getIsCapsule().equals(Boolean.TRUE)) {
            return CurationMapper.supplyCapsuleCurationElementOf(true, curation.getTitle(),
                    capsuleRepository.findAllById(recommendResponse.results()));
        }

        return CurationMapper.supplyCoffeeCurationElementOf(false, curation.getTitle(),
                coffeeRepository.findAllById(recommendResponse.results()));
    }

    private CurationElement findByFlavorNote(CurationType curation, String flavor) {

        if (curation.getIsCapsule().equals(Boolean.TRUE)) {
            return CurationMapper.supplyCapsuleCurationElementOf(true, curation.getTitle(),
                    capsuleRepository.findTop10ByFlavorNoteContains(flavor));
        }

        return CurationMapper.supplyCoffeeCurationElementOf(false, curation.getTitle(),
                coffeeRepository.findTop10ByFlavorNoteContains(flavor));
    }

    private CurationElement findByAgeAndGender(CurationType curation, Age age, Gender gender) {

        PreferenceAverage average = memberQueryRepository.findPreferenceAverageByAgeAndGender(age, gender);

        RecommendResponse recommendResponse = recommendService.pickByPreference(
                new PreferenceRequest(curation.getIsCapsule(),
                        1,
                        average.getRoast(),
                        average.getAcidity(),
                        average.getBody(),
                        null));

        if (curation.getIsCapsule().equals(Boolean.TRUE)) {
            return CurationMapper.supplyCapsuleCurationElementOf(true,
                    new StringBuffer(String.valueOf(age.ordinal() + 1))
                            .append("0대 ")
                            .append(gender == Gender.MEN ? "남성" : "여성")
                            .append(" 회원들의 취향").toString(),
                    capsuleRepository.findAllById(recommendResponse.results()));
        }

        return CurationMapper.supplyCoffeeCurationElementOf(false,
                new StringBuffer(String.valueOf(age.ordinal() + 1))
                        .append("0대 ")
                        .append(gender == Gender.MEN ? "남성" : "여성")
                        .append(" 회원들의 취향").toString(),
                coffeeRepository.findAllById(recommendResponse.results()));
    }

    private CurationElement findByMemberPreference(CurationType curation, Member member) {
        Preference preference = preferenceRepository.findByMemberId(member.getId());

        RecommendResponse recommendResponse =
                recommendService.pickByPreference(SurveyMapper.supplyPreferenceRequestFrom(preference));

        if (curation.getIsCapsule().equals(Boolean.TRUE)) {
            return CurationMapper.supplyCapsuleCurationElementOf(true,
                    new StringBuffer().append(member.getNickname())
                            .append(curation.getTitle()).toString(),
                    capsuleRepository.findAllById(recommendResponse.results()));
        }

        return CurationMapper.supplyCoffeeCurationElementOf(false,
                new StringBuffer().append(member.getNickname())
                        .append(curation.getTitle()).toString(),
                coffeeRepository.findAllById(recommendResponse.results()));
    }

    private CurationElement findByMemberLikedProduct(CurationType curation, Capsule capsule) {

        RecommendResponse recommendResponse = recommendService.pickBySimilarity(true, capsule.getId());

        return CurationMapper.supplyCapsuleCurationElementOf(true,
                new StringBuffer().append(prefix)
                        .append(capsule.getCapsuleNameKr())
                        .append(curation.getTitle()).toString(),
                capsuleRepository.findAllById(recommendResponse.results()));
    }

    private CurationElement findByMemberLikedProduct(CurationType curation, Coffee coffee) {

        RecommendResponse recommendResponse = recommendService.pickBySimilarity(false, coffee.getId());

        return CurationMapper.supplyCoffeeCurationElementOf(false,
                new StringBuffer().append(prefix)
                        .append(coffee.getCoffeeNameKr())
                        .append(curation.getTitle()).toString(),
                coffeeRepository.findAllById(recommendResponse.results()));
    }
}
