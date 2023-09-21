package com.ssafy.coffeeing.modules.search.dto;

import java.util.Set;

public record SearchProductResponse(
        Set<ProductSearchElement> products,

        Integer totalCount
) {
}
