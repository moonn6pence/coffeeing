package com.ssafy.coffeeing.modules.auth.dto;

public record SignInResponse(
	String accessToken,
	String refreshToken,
	String grantType
) {
}
