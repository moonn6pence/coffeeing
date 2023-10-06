package com.ssafy.coffeeing.modules.search.domain;

import com.ssafy.coffeeing.modules.product.domain.ProductType;

public record Tag(
        Long tagId,
        ProductType category,
        String name
) { }
