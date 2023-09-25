package com.ssafy.coffeeing.modules.search.controller;

import com.ssafy.coffeeing.modules.search.dto.SearchProductRequest;
import com.ssafy.coffeeing.modules.search.dto.SearchProductResponse;
import com.ssafy.coffeeing.modules.search.dto.SearchTagRequest;
import com.ssafy.coffeeing.modules.search.dto.TagsResponse;
import com.ssafy.coffeeing.modules.search.service.SearchService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @ApiOperation(value = "태그에 사용할 키워드 검색")
    @GetMapping("/tags")
    public BaseResponse<TagsResponse> getProductsBySuggestion(SearchTagRequest searchTagRequest) {
        return BaseResponse.<TagsResponse>builder()
                .data(searchService.getProductsBySuggestion(searchTagRequest))
                .build();
    }
    @ApiOperation(value = "캡슐 및 원두 검색")
    @GetMapping("/products")
    public BaseResponse<SearchProductResponse> getProductsBySearch(SearchProductRequest searchProductRequest) {
        return BaseResponse.<SearchProductResponse>builder()
                .data(searchService.getProductsBySearch(searchProductRequest))
                .build();
    }
}
