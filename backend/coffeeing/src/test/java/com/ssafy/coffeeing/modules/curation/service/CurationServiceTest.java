package com.ssafy.coffeeing.modules.curation.service;

import com.ssafy.coffeeing.dummy.CapsuleTestDummy;
import com.ssafy.coffeeing.dummy.CoffeeTestDummy;
import com.ssafy.coffeeing.modules.curation.domain.CurationType;
import com.ssafy.coffeeing.modules.curation.dto.CurationElement;
import com.ssafy.coffeeing.modules.curation.dto.CurationResponse;
import com.ssafy.coffeeing.modules.curation.mapper.CurationMapper;
import com.ssafy.coffeeing.modules.member.repository.MemberQueryRepository;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleReviewQueryRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeReviewQueryRepository;
import com.ssafy.coffeeing.modules.recommend.service.RecommendService;
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
    private CapsuleReviewQueryRepository capsuleReviewQueryRepository;
    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    private CoffeeReviewQueryRepository coffeeReviewQueryRepository;
    @Autowired
    private MemberQueryRepository memberQueryRepository;
    @Autowired
    private CurationService curationService;

    private List<Capsule> capsules;
    private List<Capsule> pickByPreferenceMockCapsules;
    private List<Capsule> fruityMockCapsules;
    private List<Capsule> pickBySimilarityMockCapsules;
    private List<Capsule> pickByCriteriaMockCapsules;
    private List<Coffee> coffees;
    private List<Coffee> fruityMockCoffees;
    private List<Coffee> pickByCriteriaMockCoffees;
    private List<Coffee> pickByPreferenceMockCoffees;
    private List<Coffee> pickBySimilarityMockCoffees;

    @BeforeEach
    void setCurations() {
        capsules = new ArrayList<>();
        fruityMockCapsules = capsuleRepository.saveAll(CapsuleTestDummy.create10SpecifiedFlavorCapsules("fruity"));
        capsules.addAll(fruityMockCapsules);
        capsules.addAll(capsuleRepository.saveAll(CapsuleTestDummy.create10SpecifiedFlavorCapsules("nutty")));
        capsules.addAll(capsuleRepository.saveAll(CapsuleTestDummy.create10SpecifiedFlavorCapsules("floral")));

        coffees = new ArrayList<>();
        fruityMockCoffees = coffeeRepository.saveAll(CoffeeTestDummy.create10SpecifiedFlavorCoffees("fruity"));
        coffees.addAll(fruityMockCoffees);
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
    void Given_IsCapsule_When_GetOpenCuration_Then_Success(Boolean isCapsule) {

        // given
        String flavor = "fruity";
        // mocking popularity curation
        CurationElement expectedPopularityCuration =
                isCapsule ? CurationMapper.supplyCapsuleCurationElementOf(
                        true,
                        CurationType.CAPSULE_POPULAR.getTitle(),
                        capsuleRepository.findTop10CapsulesByOrderByPopularityDesc()
                ) : CurationMapper.supplyCoffeeCurationElementOf(
                        false,
                        CurationType.COFFEE_POPULAR.getTitle(),
                        coffeeRepository.findTop10CoffeesByOrderByPopularityDesc()
                );

        // mocking flavor curation
        CurationElement expectedFlavorCuration =
                isCapsule ? CurationMapper.supplyCapsuleCurationElementOf(
                        true,
                        CurationType.CAPSULE_FLAVOR.getTitle(),
                        capsuleRepository.findTop10ByFlavorNoteContains(flavor)
                ) : CurationMapper.supplyCoffeeCurationElementOf(
                        false,
                        CurationType.COFFEE_FLAVOR.getTitle(),
                        coffeeRepository.findTop10ByFlavorNoteContains(flavor)
                );

        CurationResponse expected = CurationMapper
                .supplyCurationResponseFrom(Arrays.asList(expectedPopularityCuration, expectedFlavorCuration));

        given(randomUtil.getRandomCharacteristicCuration(isCapsule))
                .willReturn(isCapsule ? CurationType.CAPSULE_FLAVOR : CurationType.COFFEE_FLAVOR);
        given(randomUtil.getRandomFlavor()).willReturn(flavor);

        // when
        CurationResponse actual = curationService.getOpenCuration(isCapsule);

        // then
        assertEquals(expected, actual);
    }

}