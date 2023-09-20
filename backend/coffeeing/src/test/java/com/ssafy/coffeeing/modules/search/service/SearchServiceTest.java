package com.ssafy.coffeeing.modules.search.service;

import com.ssafy.coffeeing.dummy.CapsuleTestDummy;
import com.ssafy.coffeeing.dummy.CoffeeTestDummy;
import com.ssafy.coffeeing.dummy.TagTestDummy;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.search.dto.SearchProductRequest;
import com.ssafy.coffeeing.modules.search.dto.SearchProductResponse;
import com.ssafy.coffeeing.modules.search.dto.SearchTagRequest;
import com.ssafy.coffeeing.modules.search.dto.TagsResponse;
import com.ssafy.coffeeing.modules.search.service.SearchService;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

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
        "20, 25, , fruits",
        ", 50, , ",
        ", , 90, Chocolate",
        "80, , 60, ",
        ", , , Nutty",
        ", , , 'Fruits,Nutty'",
        "20, 75, 30, "
    })
    void Given_RequestSearchWithFiltering_When_Search_Then_Success(Integer roast, Integer acidity, Integer body, String flavorNote) {
        //given
        SearchProductRequest searchProductRequest = new SearchProductRequest(roast, acidity, body, flavorNote);

        //when
        SearchProductResponse searchProductResponse = searchService.getProductsBySearch(searchProductRequest);

        //then
        assertEquals(, searchProductResponse);
    }
}