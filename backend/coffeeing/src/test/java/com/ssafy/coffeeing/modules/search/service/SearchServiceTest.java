package com.ssafy.coffeeing.modules.search.service;

import com.ssafy.coffeeing.dummy.CapsuleTestDummy;
import com.ssafy.coffeeing.dummy.CoffeeTestDummy;
import com.ssafy.coffeeing.dummy.TagTestDummy;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.search.domain.TagType;
import com.ssafy.coffeeing.modules.search.dto.SearchProductRequest;
import com.ssafy.coffeeing.modules.search.dto.SearchProductResponse;
import com.ssafy.coffeeing.modules.search.dto.SearchTagRequest;
import com.ssafy.coffeeing.modules.search.dto.TagsResponse;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
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

    @DisplayName("캡슐 및 원두 검색 시, 검색에 성공한다.")
    @ParameterizedTest
    @CsvSource({
        "light, unknown, , Fruits, 0",
        ", low, , ,1",
        ", , heavy, Chocolate, 2",
        "medium_dark, , medium, ,3",
        ", , , Nutty,4",
        ", , , 'Fruits,Nutty',5",
        ", , , Fruits,6",
        "light, medium, light, ,7",
        "'light, medium_light', , , ,8"
    })
    void Given_RequestSearchWithFiltering_When_Search_Then_Success(
            String roast,
            String acidity,
            String body,
            String flavorNote,
            int index) {
        //given
        capsuleRepository.saveAll(CapsuleTestDummy.createSearchResultExpectCapsules());
        SearchProductRequest searchProductRequest = new SearchProductRequest(roast, acidity, body,
                flavorNote, TagType.CAPSULE, null, null);

        //when
        SearchProductResponse searchProductResponse = searchService.getProductsBySearch(searchProductRequest);

        //then
        assertThat(searchProductResponse.products().size()).isEqualTo(CapsuleTestDummy.expectedSearchCount(index));
    }
}