package com.ssafy.coffeeing.modules.search.domain;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.SearchErrorInfo;

import java.util.Arrays;

public enum Body {
    LIGHT(0.0, 0.3),
    MEDIUM(0.3, 0.6),
    HEAVY(0.6, 0.9);

    private final Double minValue;
    private final Double maxValue;

    Body(Double minValue, Double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public Double getMinValue() {
        return this.minValue;
    }

    public Double getMaxValue() {
        return this.maxValue;
    }

    public static Body findBody(String degree) {
        return Arrays.stream(Body.values())
                .filter(body -> body.name().equals(degree.toUpperCase()))
                .findFirst().orElseThrow(() -> new BusinessException(SearchErrorInfo.NOT_EXIST_BODY));
    }
}
