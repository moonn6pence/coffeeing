package com.ssafy.coffeeing.modules.global.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

	/**
	 * AuthenticationProvider는
	 * AuthenticationManager에 의해 호출되며, userDetailsServcie를 통해 사용자에 대한 정보를 조회,
	 * PasswordEncoder를 사용해 검증을 진행한다.
	 * */
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String rawPassword = authentication.getCredentials().toString();
		UserDetails memberDetails = userDetailsService.loadUserByUsername(email);
		if(passwordEncoder.matches(rawPassword, memberDetails.getPassword())) {
			return new UsernamePasswordAuthenticationToken(email, "");
		}

		throw new RuntimeException();
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}