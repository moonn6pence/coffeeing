package com.ssafy.coffeeing.modules.global.exception.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchErrorInfo implements ErrorInfo {

    NOT_EXIST_ROAST("1100", "ROAST 정보를 찾을 수 없습니다"),
    NOT_EXIST_BODY("1101", "BODY 정보를 찾을 수 없습니다"),
    NOT_EXIST_ACIDITY("1102", "ACIDITY 정보를 찾을 수 없습니다");

    private final String code;
    private final String message;
}
