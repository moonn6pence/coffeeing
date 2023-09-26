package com.ssafy.coffeeing.modules.search.dto;

import lombok.Builder;

import java.util.Objects;

public record SearchProductRequest(
        String keyword,
        String roast,
        String acidity,
        String body,
        String flavorNote,
        Integer page,
        Integer size

) {

    @Builder
    public SearchProductRequest(String keyword, String roast, String acidity, String body, String flavorNote,
                                Integer page, Integer size) {
        if(Objects.isNull(page)) {
            page = 0;
        }
        if(Objects.isNull(size)) {
            size = 8;
        }
        this.keyword = keyword;
        this.roast = roast;
        this.acidity = acidity;
        this.body = body;
        this.flavorNote = flavorNote;
        this.page = page;
        this.size = size;
    }
}
