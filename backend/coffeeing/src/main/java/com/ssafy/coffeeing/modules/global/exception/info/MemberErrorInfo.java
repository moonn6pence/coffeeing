package com.ssafy.coffeeing.modules.global.exception.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorInfo implements ErrorInfo {
	NOT_FOUND("900", "존재하지 않는 회원입니다."),
	PRE_EXIST_EMAIL("901", "이미 존재하는 회원입니다."),
	PRE_EXIST_NICKNAME("902", "이미 존재하는 닉네임입니다.");
	private String code;
	private String message;
}
