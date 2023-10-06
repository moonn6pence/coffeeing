package com.ssafy.coffeeing.modules.auth.dto;

public record SignUpResponse(
	Long memberId,
	String email,
	String accessToken,
	String refreshToken,

	String grantType
) {
}
