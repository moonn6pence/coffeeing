package com.ssafy.coffeeing.modules.search.domain;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.SearchErrorInfo;

import java.util.Arrays;

public enum Roast {
    LIGHT(0.0, 0.2),
    MEDIUM_LIGHT(0.2, 0.4),
    MEDIUM(0.4, 0.6),
    MEDIUM_DARK(0.6, 0.8),
    DARK(0.8, 1.0);


    private final Double minValue;
    private final Double maxValue;

    Roast(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public Double getMinValue() {
        return this.minValue;
    }

    public Double getMaxValue() {
        return this.maxValue;
    }

    public static Roast findRoast(String degree) {
        return Arrays.stream(Roast.values())
                .filter(roast -> roast.name().equals(degree.toUpperCase()))
                .findFirst().orElseThrow(() -> new BusinessException(SearchErrorInfo.NOT_EXIST_ROAST));
    }
}
