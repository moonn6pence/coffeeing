package com.ssafy.coffeeing.modules.auth.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public record SignInRequest(
	String email,
	String password
) {
	public Authentication getMemberEmailAndPasswordAuthentication() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}
}
