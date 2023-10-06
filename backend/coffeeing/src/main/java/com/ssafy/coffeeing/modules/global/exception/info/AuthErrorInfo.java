package com.ssafy.coffeeing.modules.global.exception.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorInfo implements ErrorInfo {

	ACCESS_TOKEN_EXPIRED("600", "만료된 토큰입니다."),
	NOT_VALID_TOKEN("601", "유효하지 않은 토큰입니다."),
	UNAUTHORIZED("602", "권한이 없습니다.");

	private final String code;
	private final String message;
}
