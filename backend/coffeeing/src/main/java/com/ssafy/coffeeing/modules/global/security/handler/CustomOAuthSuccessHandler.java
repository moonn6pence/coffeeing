package com.ssafy.coffeeing.modules.global.security.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.coffeeing.modules.auth.domain.OAuth2Member;
import com.ssafy.coffeeing.modules.auth.dto.SignInResponse;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuthSuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON.getType());
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		OAuth2Member oAuth2Member = (OAuth2Member) authentication.getPrincipal();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(response.getWriter(), BaseResponse.builder()
				.data(new SignInResponse(
						oAuth2Member.getAccessToken(),
						oAuth2Member.getRefreshToken(),
						oAuth2Member.getGrantType()))
				.build());
	}
}
