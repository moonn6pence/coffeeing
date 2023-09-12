package com.ssafy.coffeeing.modules.global.security.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;

public class JWTExceptionHandleFilter extends OncePerRequestFilter {
	private static final String NOT_VALID_TOKEN = "601";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			filterChain.doFilter(request, response);
		} catch (BusinessException e) {
			HttpStatus httpStatus = switch (e.getInfo().getCode()) {
				case NOT_VALID_TOKEN -> HttpStatus.BAD_REQUEST;
				default -> HttpStatus.INTERNAL_SERVER_ERROR;
			};
			response.setStatus(httpStatus.value());
			response.setContentType(MediaType.APPLICATION_JSON.getType());
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(response.getWriter(), BaseResponse.builder()
				.code(e.getInfo().getCode())
				.message(e.getInfo().getMessage())
				.build());
		}
	}
}
