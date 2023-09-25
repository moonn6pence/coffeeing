package com.ssafy.coffeeing.modules.search.controller;

import com.ssafy.coffeeing.modules.search.dto.SearchProductRequest;
import com.ssafy.coffeeing.modules.search.dto.SearchProductResponse;
import com.ssafy.coffeeing.modules.search.dto.SearchTagRequest;
import com.ssafy.coffeeing.modules.search.dto.TagsResponse;
import com.ssafy.coffeeing.modules.search.service.SearchService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/tags")
    public BaseResponse<TagsResponse> getProductsBySuggestion(SearchTagRequest searchTagRequest) {
        return BaseResponse.<TagsResponse>builder()
                .data(searchService.getProductsBySuggestion(searchTagRequest))
                .build();
    }

    @GetMapping("/products")
    public BaseResponse<SearchProductResponse> getProductsBySearch(SearchProductRequest searchProductRequest) {
        return BaseResponse.<SearchProductResponse>builder()
                .data(searchService.getProductsBySearch(searchProductRequest))
                .build();
    }
}
