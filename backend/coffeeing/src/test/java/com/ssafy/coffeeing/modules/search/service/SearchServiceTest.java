package com.ssafy.coffeeing.modules.search.service;

import com.ssafy.coffeeing.dummy.CapsuleTestDummy;
import com.ssafy.coffeeing.dummy.CoffeeTestDummy;
import com.ssafy.coffeeing.dummy.TagTestDummy;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.search.dto.SearchCapsuleResponse;
import com.ssafy.coffeeing.modules.search.dto.SearchProductRequest;
import com.ssafy.coffeeing.modules.search.dto.SearchTagRequest;
import com.ssafy.coffeeing.modules.search.dto.TagsResponse;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchServiceTest extends ServiceTest {

    @Autowired
    SearchService searchService;

    @Autowired
    CapsuleRepository capsuleRepository;

    @Autowired
    CoffeeRepository coffeeRepository;

    @DisplayName("태그 검색 시, 키워드와 관련된 태그들을 최대 10개를 조회하는데 성공한다.")
    @Test
    void Given_KeywordRequest_When_SearchTag_Then_Success() {
        //given
        SearchTagRequest searchTagRequest = TagTestDummy.createSearchTagRequest("로마");
        Capsule capsule = CapsuleTestDummy.createMockCapsuleRoma();
        Coffee coffee = CoffeeTestDummy.createMockCoffeeRoma();
        capsuleRepository.save(capsule);
        coffeeRepository.save(coffee);

        //when
        TagsResponse tagsResponse = searchService.getProductsBySuggestion(searchTagRequest);

        //then
        assertEquals(tagsResponse.tags().size(), 2);
    }

    @DisplayName("캡슐 검색 시, 캡슐 검색 결과를 제공한다.")
    @ParameterizedTest
    @MethodSource("provideSearchProductRequestAboutCapsule")
    void Given_WithKeywordRequest_When_SearchCapsule_Then_Fail(
            String keyword,
            String roast,
            String acidity,
            String body,
            String flavorNote,
            int index) {
        //given
        capsuleRepository.saveAll(CapsuleTestDummy.createSearchResultExpectCapsules());
        SearchProductRequest searchProductRequest = new SearchProductRequest(keyword, roast, acidity, body,
                flavorNote,null, null);

        //when
        SearchCapsuleResponse searchCapsuleResponse = searchService.getProductsBySearchCapsule(searchProductRequest);

        //then
        assertThat(searchCapsuleResponse.products().size())
                .isEqualTo(CapsuleTestDummy.expectedSearchCount(index));
    }

    private static Stream<Arguments> provideSearchProductRequestAboutCapsule() {
        return Stream.of(
                Arguments.of("예가체프", "light", null, null, null, 0),
                Arguments.of("Panama", "medium_dark", "medium", null, null, 1),
                Arguments.of(null, "light", "unknown", null, "Fruits", 2),
                Arguments.of(null, null, "low", null, null, 3),
                Arguments.of(null, null, null, "heavy", "Chocolate", 4),
                Arguments.of(null, "medium_dark", null, "medium", null,  5),
                Arguments.of(null, null, null, null, "Nutty", 6),
                Arguments.of(null, null, null, null, "Fruits, Nutty", 7),
                Arguments.of(null, null, null, null, "Fruits", 8),
                Arguments.of(null, "light", "medium", "light", null, 9),
                Arguments.of(null, "light, medium_light", null, null, null, 10),
                Arguments.of(null, null, null, null, null, 11));
    }
}