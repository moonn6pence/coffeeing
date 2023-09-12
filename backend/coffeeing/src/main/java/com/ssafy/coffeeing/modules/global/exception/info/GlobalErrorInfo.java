package com.ssafy.coffeeing.modules.global.exception.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorInfo implements ErrorInfo{
    INTERNAL_SERVER_ERROR("500","서버 내부 에러가 발생했습니다."),
    BAD_REQUEST("400","잘못된 요청입니다."),
    NOT_FOUND("404", "잘못된 경로입니다.");

    private String code;
    private String message;
}
