package com.ssafy.coffeeing.modules.search.domain;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.SearchErrorInfo;

import java.util.Arrays;

public enum Acidity {
    UNKNOWN(0.0, 0.25),
    LOW(0.25, 0.5),
    MEDIUM(0.5, 0.75),
    HIGH(0.75, 1.0);

    private final Double minValue;
    private final Double maxValue;

    Acidity(Double minValue, Double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public Double getMinValue() {
        return this.minValue;
    }

    public Double getMaxValue() {
        return this.maxValue;
    }

    public Acidity findBody(String degree) {
        return Arrays.stream(Acidity.values())
                .filter(acidity -> acidity.name().equals(degree.toUpperCase()))
                .findFirst().orElseThrow(() -> new BusinessException(SearchErrorInfo.NOT_EXIST_ACIDITY));
    }
}
