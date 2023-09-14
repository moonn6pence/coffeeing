package com.ssafy.coffeeing.modules.global.security;

import static org.junit.jupiter.api.Assertions.*;

import com.ssafy.coffeeing.modules.auth.dto.SignInRequest;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.MemberErrorInfo;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

class CustomAuthenticationProviderTest extends ServiceTest {

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Test
	@DisplayName("유효한 이메일, 닉네임 입력 시 사용자 검증 성공")
	void Given_ValidEmailAndValidPassword_When_Authentication_Then_Success() {
		//given
		SignInRequest signInRequest = new SignInRequest(generalMember.getEmail(), "test123");
		//when
		Authentication authenticate = authenticationProvider.authenticate(signInRequest.getMemberEmailAndPasswordAuthentication());
		//then
		assertEquals(generalMember.getEmail(), authenticate.getName());
	}

	@Test
	@DisplayName("존재 하지 않은 이메일에 대한 사용자 검증 실패")
	void Given_NotValidEmail_When_Authentication_Then_Throw_NOT_VALID_LOGIN() {
		//given
		SignInRequest signInRequest = new SignInRequest("teaaast@Naver.com", "password");

		//when, then
		assertEquals(MemberErrorInfo.NOT_FOUND,
				assertThrows(BusinessException.class, () -> authenticationProvider
						.authenticate(signInRequest.getMemberEmailAndPasswordAuthentication())).getInfo());
	}

	@Test
	@DisplayName("유효 하지 않은 암호 입력에 대한 사용자 검증 실패")
	void Given_NotValidPassword_When_Authentication_Then_Throw_NOT_VALID_LOGIN() {
		//given
		SignInRequest signInRequest = new SignInRequest(generalMember.getEmail(), "wrongPassword!");
		//when, then
		assertEquals(MemberErrorInfo.NOT_VALID_LOGIN,
				assertThrows(BusinessException.class, () -> authenticationProvider
						.authenticate(signInRequest.getMemberEmailAndPasswordAuthentication())).getInfo());
	}
}