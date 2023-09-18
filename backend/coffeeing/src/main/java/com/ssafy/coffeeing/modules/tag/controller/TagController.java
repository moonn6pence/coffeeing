package com.ssafy.coffeeing.modules.tag.controller;

import com.ssafy.coffeeing.modules.tag.dto.TagsResponse;
import com.ssafy.coffeeing.modules.tag.dto.SearchTagRequest;
import com.ssafy.coffeeing.modules.tag.service.TagService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/search")
    public BaseResponse<TagsResponse> getProductsBySuggestion(SearchTagRequest searchTagRequest) {
        return BaseResponse.<TagsResponse>builder()
                .data(tagService.getProductsBySuggestion(searchTagRequest))
                .build();
    }
}
