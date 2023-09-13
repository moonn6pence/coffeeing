package com.ssafy.coffeeing.modules.auth.dto;

public record ReissueResponse(
	String email,
	String accessToken,
	String grantType
) {
}
