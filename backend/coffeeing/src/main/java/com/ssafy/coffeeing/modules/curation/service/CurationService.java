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
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
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
    private static final String prefix = "당신이 좋아하는 ";

    @Transactional(readOnly = true)
    public CurationResponse getOpenCuration(Boolean isCapsule) {

        List<CurationElement> curations = new ArrayList<>();

        curations.add(findByPopularity(isCapsule ? CurationType.CAPSULE_POPULAR : CurationType.COFFEE_POPULAR));

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

        if (isCapsule) {
            Capsule capsule = capsuleReviewQueryRepository.findRandomHighScoredCapsule(member);

            if (capsule != null && randomCuration.equals(CurationType.CAPSULE_LIKED_PRODUCT)) {
                curations.add(findByMemberLikedProduct(randomCuration, capsule));
            } else {
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

        if (curation.getIsCapsule()) {
            return CurationMapper.supplyCapsuleCurationElementOf(true, curation.getTitle(),
                    capsuleRepository.findTop10CapsulesByOrderByPopularityDesc());
        }

        return CurationMapper.supplyCoffeeCurationElementOf(false, curation.getTitle(),
                coffeeRepository.findTop10CapsulesByOrderByPopularityDesc());
    }

    private CurationElement findByCharacteristic(CurationType curation) {

        RecommendResponse recommendResponse = recommendService.pickByCriteria(
                curation.getIsCapsule(),
                curation.getCriteria(),
                curation.getAttribute()
        );

        if (curation.getIsCapsule()) {
            return CurationMapper.supplyCapsuleCurationElementOf(true, curation.getTitle(),
                    capsuleRepository.findAllById(recommendResponse.results()));
        }

        return CurationMapper.supplyCoffeeCurationElementOf(false, curation.getTitle(),
                coffeeRepository.findAllById(recommendResponse.results()));
    }

    private CurationElement findByFlavorNote(CurationType curation, String flavor) {

        if (curation.getIsCapsule()) {
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

        if (curation.getIsCapsule()) {
            return CurationMapper.supplyCapsuleCurationElementOf(true, curation.getTitle(),
                    capsuleRepository.findAllById(recommendResponse.results()));
        }

        return CurationMapper.supplyCoffeeCurationElementOf(false, curation.getTitle(),
                coffeeRepository.findAllById(recommendResponse.results()));
    }

    private CurationElement findByMemberLikedProduct(CurationType curation, Capsule capsule) {

        RecommendResponse recommendResponse = recommendService.pickBySimilarity(true, capsule.getId());

        return CurationMapper.supplyCapsuleCurationElementOf(true,
                new StringBuilder().append(prefix)
                        .append(capsule.getCapsuleNameKr())
                        .append(curation.getTitle()).toString(),
                capsuleRepository.findAllById(recommendResponse.results()));
    }

    private CurationElement findByMemberLikedProduct(CurationType curation, Coffee coffee) {

        RecommendResponse recommendResponse = recommendService.pickBySimilarity(false, coffee.getId());

        return CurationMapper.supplyCoffeeCurationElementOf(true,
                new StringBuilder().append(prefix)
                        .append(coffee.getCoffeeNameKr())
                        .append(curation.getTitle()).toString(),
                coffeeRepository.findAllById(recommendResponse.results()));
    }
}
