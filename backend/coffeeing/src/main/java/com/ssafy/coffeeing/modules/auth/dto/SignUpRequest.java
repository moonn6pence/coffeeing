package com.ssafy.coffeeing.modules.auth.dto;

public record SignUpRequest(
	String email,
	String password
) {
}
