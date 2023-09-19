package com.ssafy.coffeeing.modules.recommend.dto;

import java.util.List;

public record RecommendResponse(
    List<Long> results
) {
}
