package com.ssafy.coffeeing.modules.curation.dto;

import com.ssafy.coffeeing.modules.product.dto.SimpleProductElement;

import java.util.List;

public record CurationElement(
        String title,
        Boolean isCapsule,
        List<SimpleProductElement> products
) {
}
