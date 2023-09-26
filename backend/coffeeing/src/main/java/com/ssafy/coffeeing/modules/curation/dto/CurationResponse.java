package com.ssafy.coffeeing.modules.curation.dto;

import java.util.List;

public record CurationResponse(
        List<CurationElement> curations
) {
}