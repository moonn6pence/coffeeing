package com.ssafy.coffeeing.modules.global.exception.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InfraErrorInfo implements ErrorInfo{

    NO_CACHE("1300", "캐시 정보가 없습니다.");

    private final String code;
    private final String message;
}
