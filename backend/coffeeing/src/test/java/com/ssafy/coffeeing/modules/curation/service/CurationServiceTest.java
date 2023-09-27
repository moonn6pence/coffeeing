package com.ssafy.coffeeing.modules.curation.service;

import com.ssafy.coffeeing.dummy.CapsuleReviewTestDummy;
import com.ssafy.coffeeing.dummy.CapsuleTestDummy;
import com.ssafy.coffeeing.dummy.CoffeeReviewTestDummy;
import com.ssafy.coffeeing.dummy.CoffeeTestDummy;
import com.ssafy.coffeeing.modules.curation.domain.CurationType;
import com.ssafy.coffeeing.modules.curation.dto.CurationElement;
import com.ssafy.coffeeing.modules.curation.dto.CurationResponse;
import com.ssafy.coffeeing.modules.curation.mapper.CurationMapper;
import com.ssafy.coffeeing.modules.global.embedded.CoffeeCriteria;
import com.ssafy.coffeeing.modules.member.domain.Gender;
import com.ssafy.coffeeing.modules.member.repository.MemberQueryRepository;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.ProductType;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleReviewQueryRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleReviewRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeReviewQueryRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeReviewRepository;
import com.ssafy.coffeeing.modules.recommend.dto.RecommendResponse;
import com.ssafy.coffeeing.modules.recommend.service.RecommendService;
import com.ssafy.coffeeing.modules.survey.domain.Preference;
import com.ssafy.coffeeing.modules.survey.mapper.SurveyMapper;
import com.ssafy.coffeeing.modules.survey.repository.PreferenceRepository;
import com.ssafy.coffeeing.modules.util.RandomUtil;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

class CurationServiceTest extends ServiceTest {

    @MockBean
    private RecommendService recommendService;
    @MockBean
    private RandomUtil randomUtil;
    @Autowired
    private CapsuleRepository capsuleRepository;
    @Autowired
    private CapsuleReviewRepository capsuleReviewRepository;
    @Autowired
    private CapsuleReviewQueryRepository capsuleReviewQueryRepository;
    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    private CoffeeReviewRepository coffeeReviewRepository;
    @Autowired
    private CoffeeReviewQueryRepository coffeeReviewQueryRepository;
    @Autowired
    private MemberQueryRepository memberQueryRepository;
    @Autowired
    private CurationService curationService;
    @Autowired
    private PreferenceRepository preferenceRepository;

    private List<Capsule> capsules;
    private List<Capsule> pickByPreferenceMockCapsules;
    private List<Capsule> pickBySimilarityMockCapsules;
    private List<Capsule> pickByCriteriaMockCapsules;
    private List<Coffee> coffees;
    private List<Coffee> pickByCriteriaMockCoffees;
    private List<Coffee> pickByPreferenceMockCoffees;
    private List<Coffee> pickBySimilarityMockCoffees;

    @BeforeEach
    void setCurations() {
        capsules = new ArrayList<>();
        capsules.addAll(capsuleRepository.saveAll(CapsuleTestDummy.create10SpecifiedFlavorCapsules("fruity")));
        capsules.addAll(capsuleRepository.saveAll(CapsuleTestDummy.create10SpecifiedFlavorCapsules("nutty")));
        capsules.addAll(capsuleRepository.saveAll(CapsuleTestDummy.create10SpecifiedFlavorCapsules("floral")));

        coffees = new ArrayList<>();
        coffees.addAll(coffeeRepository.saveAll(CoffeeTestDummy.create10SpecifiedFlavorCoffees("fruity")));
        coffees.addAll(coffeeRepository.saveAll(CoffeeTestDummy.create10SpecifiedFlavorCoffees("nutty")));
        coffees.addAll(coffeeRepository.saveAll(CoffeeTestDummy.create10SpecifiedFlavorCoffees("floral")));

        pickByPreferenceMockCapsules = capsules.subList(0, 10);
        pickBySimilarityMockCapsules = capsules.subList(10, 20);
        pickByCriteriaMockCapsules = capsules.subList(20, 30);

        pickByCriteriaMockCoffees = coffees.subList(0, 10);
        pickByPreferenceMockCoffees = coffees.subList(10, 20);
        pickBySimilarityMockCoffees = coffees.subList(20, 30);
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    @DisplayName("공개 큐레이션 조회 요청 시 인기순과 랜덤 맛 큐레이션을 응답한다.")
    void Given_IsCapsuleAndFlavorCuration_When_GetOpenCuration_Then_Success(Boolean isCapsule) {

        // given
        String flavor = "fruity";
        // mocking popularity curation
        CurationElement expectedPopularityCuration = isCapsule
                ? CurationMapper.supplyCapsuleCurationElementOf(
                true,
                CurationType.CAPSULE_POPULAR.getTitle(),
                capsuleRepository.findTop10CapsulesByOrderByPopularityDesc())
                : CurationMapper.supplyCoffeeCurationElementOf(
                false,
                CurationType.COFFEE_POPULAR.getTitle(),
                coffeeRepository.findTop10CoffeesByOrderByPopularityDesc());

        // mocking flavor curation
        CurationElement expectedFlavorCuration = isCapsule
                ? CurationMapper.supplyCapsuleCurationElementOf(
                true,
                CurationType.CAPSULE_FLAVOR.getTitle(),
                capsuleRepository.findTop10ByFlavorNoteContains(flavor))
                : CurationMapper.supplyCoffeeCurationElementOf(
                false,
                CurationType.COFFEE_FLAVOR.getTitle(),
                coffeeRepository.findTop10ByFlavorNoteContains(flavor));

        CurationResponse expected = CurationMapper
                .supplyCurationResponseFrom(Arrays.asList(expectedPopularityCuration, expectedFlavorCuration));

        given(randomUtil.getRandomCharacteristicCuration(isCapsule))
                .willReturn(isCapsule
                        ? CurationType.CAPSULE_FLAVOR
                        : CurationType.COFFEE_FLAVOR
                );

        given(randomUtil.getRandomFlavor()).willReturn(flavor);

        // when
        CurationResponse actual = curationService.getOpenCuration(isCapsule);

        // then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    @DisplayName("공개 큐레이션 조회 요청 시 인기순과 랜덤 지표 큐레이션을 응답한다.")
    void Given_IsCapsuleAndCriteriaCuration_When_GetOpenCuration_Then_Success(Boolean isCapsule) {

        // given

        // mocking popularity curation
        CurationElement expectedPopularityCuration = isCapsule
                ? CurationMapper.supplyCapsuleCurationElementOf(
                true,
                CurationType.CAPSULE_POPULAR.getTitle(),
                capsuleRepository.findTop10CapsulesByOrderByPopularityDesc())
                : CurationMapper.supplyCoffeeCurationElementOf(
                false,
                CurationType.COFFEE_POPULAR.getTitle(),
                coffeeRepository.findTop10CoffeesByOrderByPopularityDesc());

        // mocking criteria curation
        CurationElement expectedCriteriaCuration = isCapsule
                ? CurationMapper.supplyCapsuleCurationElementOf(
                true,
                CurationType.CAPSULE_ROAST_DARK.getTitle(),
                pickByCriteriaMockCapsules)
                : CurationMapper.supplyCoffeeCurationElementOf(
                false,
                CurationType.COFFEE_ROAST_DARK.getTitle(),
                pickByCriteriaMockCoffees);

        CurationResponse expected = CurationMapper
                .supplyCurationResponseFrom(Arrays.asList(expectedPopularityCuration, expectedCriteriaCuration));

        given(randomUtil.getRandomCharacteristicCuration(isCapsule))
                .willReturn(isCapsule
                        ? CurationType.CAPSULE_ROAST_DARK
                        : CurationType.COFFEE_ROAST_DARK
                );

        given(recommendService.pickByCriteria(isCapsule, "roast", "high"))
                .willReturn(new RecommendResponse(isCapsule
                        ? pickByCriteriaMockCapsules.stream().map(Capsule::getId).toList()
                        : pickByCriteriaMockCoffees.stream().map(Coffee::getId).toList())
                );

        // when
        CurationResponse actual = curationService.getOpenCuration(isCapsule);

        // then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    @DisplayName("커스텀 큐레이션 조회 요청 시 사용자 성향 큐레이션과 나이 성별 성향 평균 큐레이션을 응답한다.")
    void Given_IsCapsuleAndAuthenticatedMemberAndAgeGenderCuration_When_GetCustomCuration_Then_Success(Boolean isCapsule) {

        // given
        Preference preference = preferenceRepository.save(Preference.builder()
                .productType(isCapsule ? ProductType.COFFEE_CAPSULE : ProductType.COFFEE_BEAN)
                .machineType(1)
                .coffeeCriteria(new CoffeeCriteria(0.4, 0.5, 0.6))
                .memberId(generalMember.getId())
                .build());

        // mocking preference curation
        CurationElement expectedPreferenceCuration = isCapsule
                ? CurationMapper.supplyCapsuleCurationElementOf(
                true,
                new StringBuffer().append(generalMember.getNickname())
                        .append(CurationType.CAPSULE_PREFERENCE.getTitle()).toString(),
                pickByPreferenceMockCapsules)
                : CurationMapper.supplyCoffeeCurationElementOf(
                false,
                new StringBuffer().append(generalMember.getNickname())
                        .append(CurationType.COFFEE_PREFERENCE.getTitle()).toString(),
                pickByPreferenceMockCoffees);

        // mocking age gender curation
        CurationElement expectedCriteriaCuration = isCapsule
                ? CurationMapper.supplyCapsuleCurationElementOf(
                true,
                new StringBuffer(String.valueOf(generalMember.getAge().ordinal() + 1))
                        .append("0대 ")
                        .append(generalMember.getGender() == Gender.MEN ? "남성" : "여성")
                        .append(" 회원들의 취향").toString(),
                pickByPreferenceMockCapsules)
                : CurationMapper.supplyCoffeeCurationElementOf(
                false,
                new StringBuffer(String.valueOf(generalMember.getAge().ordinal() + 1))
                        .append("0대 ")
                        .append(generalMember.getGender() == Gender.MEN ? "남성" : "여성")
                        .append(" 회원들의 취향").toString(),
                pickByPreferenceMockCoffees);

        CurationResponse expected = CurationMapper
                .supplyCurationResponseFrom(Arrays.asList(expectedPreferenceCuration, expectedCriteriaCuration));

        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        given(randomUtil.getRandomKeywordCuration(isCapsule))
                .willReturn(isCapsule
                        ? CurationType.CAPSULE_AGE_GENDER
                        : CurationType.COFFEE_AGE_GENDER
                );

        given(recommendService.pickByPreference(SurveyMapper.supplyPreferenceRequestFrom(preference)))
                .willReturn(new RecommendResponse(isCapsule
                        ? pickByPreferenceMockCapsules.stream().map(Capsule::getId).toList()
                        : pickByPreferenceMockCoffees.stream().map(Coffee::getId).toList())
                );

        // when
        CurationResponse actual = curationService.getCustomCuration(isCapsule);

        // then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    @DisplayName("커스텀 큐레이션 조회 요청 시 사용자 성향 큐레이션과 고평가 제품 기반 큐레이션을 응답한다.")
    void Given_IsCapsuleAndAuthenticatedMemberAndLikedProductCuration_When_GetCustomCuration_Then_Success(Boolean isCapsule) {

        // given
        String prefix = "당신이 좋아하는 ";

        Preference preference = preferenceRepository.save(Preference.builder()
                .productType(isCapsule ? ProductType.COFFEE_CAPSULE : ProductType.COFFEE_BEAN)
                .machineType(1)
                .coffeeCriteria(new CoffeeCriteria(0.4, 0.5, 0.6))
                .memberId(generalMember.getId())
                .build());

        if (isCapsule) {
            capsuleReviewRepository.save(CapsuleReviewTestDummy.createMockCapsuleReview(capsules.get(0), generalMember, 4));
            capsuleReviewRepository.save(CapsuleReviewTestDummy.createMockCapsuleReview(capsules.get(1), generalMember, 2));
        } else {
            coffeeReviewRepository.save(CoffeeReviewTestDummy.createMockCoffeeReview(coffees.get(0), generalMember, 4));
            coffeeReviewRepository.save(CoffeeReviewTestDummy.createMockCoffeeReview(coffees.get(1), generalMember, 2));
        }

        // mocking preference curation
        CurationElement expectedPreferenceCuration = isCapsule
                ? CurationMapper.supplyCapsuleCurationElementOf(
                true,
                new StringBuffer().append(generalMember.getNickname())
                        .append(CurationType.CAPSULE_PREFERENCE.getTitle()).toString(),
                pickByPreferenceMockCapsules)
                : CurationMapper.supplyCoffeeCurationElementOf(
                false,
                new StringBuffer().append(generalMember.getNickname())
                        .append(CurationType.COFFEE_PREFERENCE.getTitle()).toString(),
                pickByPreferenceMockCoffees);

        // mocking liked product curation
        CurationElement expectedLikedProductsCuration = isCapsule
                ? CurationMapper.supplyCapsuleCurationElementOf(
                true,
                new StringBuffer().append(prefix)
                        .append(capsules.get(0).getCapsuleNameKr())
                        .append(CurationType.CAPSULE_LIKED_PRODUCT.getTitle()).toString(),
                pickBySimilarityMockCapsules)
                : CurationMapper.supplyCoffeeCurationElementOf(
                false,
                new StringBuffer().append(prefix)
                        .append(coffees.get(0).getCoffeeNameKr())
                        .append(CurationType.COFFEE_LIKED_PRODUCT.getTitle()).toString(),
                pickBySimilarityMockCoffees);

        CurationResponse expected = CurationMapper
                .supplyCurationResponseFrom(Arrays.asList(expectedPreferenceCuration, expectedLikedProductsCuration));

        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        given(randomUtil.getRandomKeywordCuration(isCapsule)).willReturn(isCapsule
                ? CurationType.CAPSULE_LIKED_PRODUCT
                : CurationType.COFFEE_LIKED_PRODUCT);

        given(recommendService.pickByPreference(SurveyMapper.supplyPreferenceRequestFrom(preference)))
                .willReturn(new RecommendResponse(isCapsule
                        ? pickByPreferenceMockCapsules.stream().map(Capsule::getId).toList()
                        : pickByPreferenceMockCoffees.stream().map(Coffee::getId).toList())
                );

        given(recommendService.pickBySimilarity(isCapsule, isCapsule ? capsules.get(0).getId() : coffees.get(0).getId()))
                .willReturn(new RecommendResponse(isCapsule
                        ? pickBySimilarityMockCapsules.stream().map(Capsule::getId).toList()
                        : pickBySimilarityMockCoffees.stream().map(Coffee::getId).toList())
                );

        // when
        CurationResponse actual = curationService.getCustomCuration(isCapsule);

        // then
        assertEquals(expected, actual);
    }


}