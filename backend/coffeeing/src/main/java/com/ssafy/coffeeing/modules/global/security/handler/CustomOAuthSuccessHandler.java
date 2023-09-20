package com.ssafy.coffeeing.modules.global.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.coffeeing.modules.auth.domain.OAuth2Member;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomOAuthSuccessHandler implements AuthenticationSuccessHandler {
	private final String REDIRECT_BASE;
	public CustomOAuthSuccessHandler(@Value("${front.redirect-url}") String url) {
		this.REDIRECT_BASE = url;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		OAuth2Member oAuth2Member = (OAuth2Member) authentication.getPrincipal();

		String redirectURL = getRedirectURL(oAuth2Member.getAccessToken(), oAuth2Member.getRefreshToken());
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		redirectStrategy.sendRedirect(request, response, redirectURL);
	}

	private String getRedirectURL(String accessToken, String refreshToken) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("at", accessToken);
		params.add("rt", refreshToken);
		return UriComponentsBuilder.fromHttpUrl(REDIRECT_BASE).queryParams(params).build().toUriString();
	}
}
