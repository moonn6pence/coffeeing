package com.ssafy.coffeeing.modules.global.exception.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductErrorInfo implements ErrorInfo{
    NOT_FOUND_PRODUCT("1000","제품을 찾을 수 없습니다."),
    NOT_FOUND_REVIEW("1001", "리뷰를 찾을 수 없습니다.");

    private String code;
    private String message;
}
