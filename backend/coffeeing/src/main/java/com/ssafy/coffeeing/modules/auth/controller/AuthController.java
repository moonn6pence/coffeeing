package com.ssafy.coffeeing.modules.auth.controller;

import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.coffeeing.modules.auth.dto.ReissueRequest;
import com.ssafy.coffeeing.modules.auth.dto.ReissueResponse;
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
	@ApiOperation(value = "로그인 요청")
	public BaseResponse<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
		Authentication authentication = authenticationManager.authenticate(signInRequest.getMemberEmailAndPasswordAuthentication());

		return BaseResponse.<SignInResponse>builder()
			.data(authService.signIn(authentication))
			.build();
	}

	@PostMapping("/sign-up")
	@ApiOperation(value = "회원가입 요청")
	public BaseResponse<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
		return BaseResponse.<SignUpResponse>builder()
			.data(authService.signUp(signUpRequest))
			.build();
	}

	@PostMapping("/reissue")
	@ApiOperation(value = "토큰 재발급 요청")
	public BaseResponse<ReissueResponse> reissueAccessToken(@RequestBody ReissueRequest reissueRequest) {
		return BaseResponse.<ReissueResponse>builder()
			.data(authService.reissueAccessToken(reissueRequest))
			.build();
	}
}
