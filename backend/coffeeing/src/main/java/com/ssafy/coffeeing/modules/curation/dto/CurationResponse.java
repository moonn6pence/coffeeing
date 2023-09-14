package com.ssafy.coffeeing.modules.curation.dto;

import com.ssafy.coffeeing.modules.product.dto.SimpleProductElement;

import java.util.List;

public record CurationResponse(
        String curationTitle,
        Boolean isCapsule,
        List<SimpleProductElement> products
) {
}