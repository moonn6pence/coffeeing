package com.ssafy.coffeeing.modules.global.security;

import static org.junit.jupiter.api.Assertions.*;

import com.ssafy.coffeeing.dummy.MemberTestDummy;
import com.ssafy.coffeeing.modules.auth.dto.SignInRequest;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.MemberErrorInfo;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.domain.MemberState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.coffeeing.modules.member.repository.MemberRepository;

@SpringBootTest
@Transactional
class CustomAuthenticationProviderTest {

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	@DisplayName("유효한 이메일, 닉네임 입력 시 사용자 검증 성공")
	void Given_ValidEmailAndValidPassword_When_Authentication_Then_Success() {
		//given
		Member member = MemberTestDummy.createMember("tim", "{noop}password", "test@Naver.com", MemberState.NORMAL);
		memberRepository.save(member);
		SignInRequest signInRequest = new SignInRequest("test@Naver.com", "password");
		//when
		Authentication authenticate = authenticationProvider.authenticate(signInRequest.getMemberEmailAndPasswordAuthentication());

		//then
		assertEquals("test@Naver.com", authenticate.getName());
	}

	@Test
	@DisplayName("존재 하지 않은 이메일에 대한 사용자 검증 실패")
	void Given_NotValidEmail_When_Authentication_Then_Throw_NOT_VALID_LOGIN() {
		//given
		SignInRequest signInRequest = new SignInRequest("test@Naver.com", "password");

		//when, then
		assertEquals(MemberErrorInfo.NOT_FOUND,
				assertThrows(BusinessException.class, () -> authenticationProvider
						.authenticate(signInRequest.getMemberEmailAndPasswordAuthentication())).getInfo());
	}

	@Test
	@DisplayName("유효 하지 않은 암호 입력에 대한 사용자 검증 실패")
	void Given_NotValidPassword_When_Authentication_Then_Throw_NOT_VALID_LOGIN() {
		//given
		Member member = MemberTestDummy.createMember("tim", "{noop}password", "test@Naver.com", MemberState.NORMAL);
		memberRepository.save(member);
		SignInRequest signInRequest = new SignInRequest("test@Naver.com", "wrongPassword!");

		//when, then
		assertEquals(MemberErrorInfo.NOT_VALID_LOGIN,
				assertThrows(BusinessException.class, () -> authenticationProvider
						.authenticate(signInRequest.getMemberEmailAndPasswordAuthentication())).getInfo());
	}
}