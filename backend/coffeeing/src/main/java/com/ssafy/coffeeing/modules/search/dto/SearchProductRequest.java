package com.ssafy.coffeeing.modules.search.dto;

public record SearchProductRequest(
        Integer roast,
        Integer acidity,
        Integer body,
        String flavorNote
) {
}
