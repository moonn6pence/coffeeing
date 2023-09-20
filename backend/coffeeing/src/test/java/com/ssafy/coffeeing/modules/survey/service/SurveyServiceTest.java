package com.ssafy.coffeeing.modules.survey.service;

import com.ssafy.coffeeing.dummy.CapsuleTestDummy;
import com.ssafy.coffeeing.dummy.CoffeeTestDummy;
import com.ssafy.coffeeing.modules.event.eventer.ExperienceEvent;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.ProductType;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.recommend.service.MockRecommendService;
import com.ssafy.coffeeing.modules.recommend.service.RecommendService;
import com.ssafy.coffeeing.modules.survey.domain.Preference;
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import com.ssafy.coffeeing.modules.survey.dto.SurveyResponse;
import com.ssafy.coffeeing.modules.survey.mapper.SurveyMapper;
import com.ssafy.coffeeing.modules.survey.repository.PreferenceRepository;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import com.ssafy.coffeeing.modules.util.base.BaseEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@RecordApplicationEvents
class SurveyServiceTest extends ServiceTest {

    @MockBean
    private SecurityContextUtils securityContextUtils;

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Qualifier("MockRecommendService")
    private RecommendService recommendService;

    @Autowired
    private CapsuleRepository capsuleRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ApplicationEvents applicationEvents;

    @Autowired
    private SurveyService surveyService;

    private List<Capsule> capsules;
    private List<Capsule> capsuleRecommendations;
    private List<Coffee> coffees;
    private List<Coffee> coffeeRecommendations;

    @ParameterizedTest
    @MethodSource("preferenceRequests")
    @DisplayName("미인증 사용자가 설문 결과를 통해 제품 추천을 요청한다.")
    void Given_ValidPreferenceRequestWithUnauthorizedUser_When_RecommendBySurvey_Then_Success(PreferenceRequest preferenceRequest) {

        // given
        if (preferenceRequest.isCapsule()) {
            capsules = capsuleRepository.saveAll(CapsuleTestDummy.create25GenericCapsules());
            capsuleRecommendations = capsules.subList(0, 4);
            ReflectionTestUtils.setField(MockRecommendService.class, "mockCapsuleIds",
                    capsuleRecommendations.stream().map(BaseEntity::getId).toList());
        } else {
            coffees = coffeeRepository.saveAll(CoffeeTestDummy.create25GeneralCoffees());
            coffeeRecommendations = coffees.subList(0, 4);
            ReflectionTestUtils.setField(MockRecommendService.class, "mockCoffeeIds",
                    coffeeRecommendations.stream().map(BaseEntity::getId).toList());
        }

        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(null);

        SurveyResponse expected = generateExpectation(preferenceRequest);

        // when
        SurveyResponse actual = surveyService.recommendBySurvey(preferenceRequest);

        // then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("preferenceRequests")
    @DisplayName("인증된 사용자가 설문 결과를 통해 제품 추천을 요청한다.")
    void Given_ValidPreferenceRequestWithAuthorizedUser_When_RecommendBySurvey_Then_Success(PreferenceRequest preferenceRequest) {

        // given
        if (preferenceRequest.isCapsule()) {
            capsules = capsuleRepository.saveAll(CapsuleTestDummy.create25GenericCapsules());
            capsuleRecommendations = capsules.subList(0, 4);
            ReflectionTestUtils.setField(MockRecommendService.class, "mockCapsuleIds",
                    capsuleRecommendations.stream().map(BaseEntity::getId).toList());
        } else {
            coffees = coffeeRepository.saveAll(CoffeeTestDummy.create25GeneralCoffees());
            coffeeRecommendations = coffees.subList(0, 4);
            ReflectionTestUtils.setField(MockRecommendService.class, "mockCoffeeIds",
                    coffeeRecommendations.stream().map(BaseEntity::getId).toList());
        }

        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(generalMember);
        SurveyResponse expected = generateExpectation(preferenceRequest, generalMember);

        // when
        SurveyResponse actual = surveyService.recommendBySurvey(preferenceRequest);

        // then
        assertEquals(expected, actual);
        assertEquals(1,(int)applicationEvents.stream(ExperienceEvent.class).count());
    }

    @Test
    @DisplayName("성향 저장 요청 시 성향 정보가 없는 멤버의 경우 성향 정보를 새로 저장한다.")
    void Given_PreferenceRequestAndNoPreferenceMember_When_SavePreference_Then_Success() {

        // given
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        PreferenceRequest preferenceRequest = new PreferenceRequest(Boolean.TRUE, 1, 0.1, 0.2, 0.9, "nutty");

        // when
        surveyService.savePreference(preferenceRequest);
        Preference actual = preferenceRepository.findByMemberId(generalMember.getId());

        // then
        assertAll(
                () -> assertEquals(preferenceRequest.isCapsule() ? ProductType.COFFEE_CAPSULE : ProductType.COFFEE_BEAN, actual.getProductType()),
                () -> assertEquals(preferenceRequest.roast(), actual.getCoffeeCriteria().getRoast()),
                () -> assertEquals(preferenceRequest.acidity(), actual.getCoffeeCriteria().getAcidity()),
                () -> assertEquals(preferenceRequest.body(), actual.getCoffeeCriteria().getBody()),
                () -> assertEquals(preferenceRequest.flavorNote(), actual.getFlavorNote())
        );
    }

    @Test
    @DisplayName("성향 저장 요청 시 성향 정보가 있는 멤버의 경우 성향 정보를 업데이트한다.")
    void Given_PreferenceRequest_When_SavePreference_Then_Success() {
        // given
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        Preference preference = preferenceRepository.save(
                SurveyMapper.supplyPreferenceOf(
                        new PreferenceRequest(Boolean.FALSE, 5, 0.1, 0.4, 0.6, "test"), generalMember.getId()
                )
        );
        PreferenceRequest preferenceRequest = new PreferenceRequest(Boolean.TRUE, 1, 0.1, 0.2, 0.9, "nutty");

        // when
        surveyService.savePreference(preferenceRequest);
        Preference actual = preferenceRepository.findByMemberId(generalMember.getId());

        // then
        assertAll(
                () -> assertEquals(preferenceRequest.isCapsule() ? ProductType.COFFEE_CAPSULE : ProductType.COFFEE_BEAN, actual.getProductType()),
                () -> assertEquals(preferenceRequest.roast(), actual.getCoffeeCriteria().getRoast()),
                () -> assertEquals(preferenceRequest.acidity(), actual.getCoffeeCriteria().getAcidity()),
                () -> assertEquals(preferenceRequest.body(), actual.getCoffeeCriteria().getBody()),
                () -> assertEquals(preferenceRequest.flavorNote(), actual.getFlavorNote())
        );
    }

    private static Stream<Arguments> preferenceRequests() {
        return Stream.of(
                Arguments.arguments(new PreferenceRequest(Boolean.TRUE, 1, 0.1, 0.2, 0.9, "nutty")),
                Arguments.arguments(new PreferenceRequest(Boolean.FALSE, null, 1.0, 0.2, 0.9, "nutty"))
        );
    }

    private SurveyResponse generateExpectation(PreferenceRequest preferenceRequest) {

        if (preferenceRequest.isCapsule()) {
            return SurveyMapper.supplySurveyResponseOf(capsuleRecommendations.stream()
                    .map(ProductMapper::supplySimpleProductElementFrom)
                    .toList(), preferenceRequest);
        }

        return SurveyMapper.supplySurveyResponseOf(coffeeRecommendations.stream()
                .map(ProductMapper::supplySimpleProductElementFrom)
                .toList(), preferenceRequest);
    }

    private SurveyResponse generateExpectation(PreferenceRequest preferenceRequest, Member member) {

        if (preferenceRequest.isCapsule()) {
            return SurveyMapper.supplySurveyResponseOf(capsuleRecommendations.stream()
                    .map(ProductMapper::supplySimpleProductElementFrom)
                    .toList(), preferenceRequest, member);
        }

        return SurveyMapper.supplySurveyResponseOf(coffeeRecommendations.stream()
                .map(ProductMapper::supplySimpleProductElementFrom)
                .toList(), preferenceRequest, member);
    }

}