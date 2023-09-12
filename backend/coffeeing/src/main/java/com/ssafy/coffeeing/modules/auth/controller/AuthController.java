package com.ssafy.coffeeing.modules.auth.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.coffeeing.modules.auth.dto.SignInRequest;
import com.ssafy.coffeeing.modules.auth.dto.SignInResponse;
import com.ssafy.coffeeing.modules.auth.dto.SignUpRequest;
import com.ssafy.coffeeing.modules.auth.dto.SignUpResponse;
import com.ssafy.coffeeing.modules.auth.service.AuthService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	private final AuthenticationManager authenticationManager;

	@PostMapping("/sign-in")
	public BaseResponse<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
		Authentication authentication = authenticationManager.authenticate(signInRequest.getMemberEmailAndPasswordAuthentication());

		return BaseResponse.<SignInResponse>builder()
			.data(authService.signIn(authentication))
			.build();
	}

	@PostMapping("/sign-up")
	public BaseResponse<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
		return BaseResponse.<SignUpResponse>builder()
			.data(authService.signUp(signUpRequest))
			.build();
	}
}
