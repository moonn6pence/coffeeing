package com.ssafy.coffeeing.modules.auth.service;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ssafy.coffeeing.dummy.MemberTestDummy;
import com.ssafy.coffeeing.modules.auth.dto.SignInRequest;
import com.ssafy.coffeeing.modules.auth.dto.SignInResponse;
import com.ssafy.coffeeing.modules.auth.dto.SignUpRequest;
import com.ssafy.coffeeing.modules.auth.dto.SignUpResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.MemberErrorInfo;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.domain.MemberState;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.util.ServiceTest;

public class AuthServiceTest extends ServiceTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AuthService authService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final String TEST_PASSWORD = "mklrgjanj3@$nDfsn";
	private final String GRANT_TYPE = "Bearer";

	@Test
	@DisplayName("이메일과 비밀번호를 입력하여 회원가입을 진행한다.")
	void Given_ValidEmailAndPassword_When_SignUp_Then_Success() {
		String email = "testmail@naver.com";
		//given
		SignUpRequest signUpRequest = new SignUpRequest(email, TEST_PASSWORD);
		//when
		SignUpResponse signUpResponse = authService.signUp(signUpRequest);
		//then
		assertAll(
			() -> assertEquals(GRANT_TYPE, signUpResponse.grantType()),
			() -> assertNotNull(signUpResponse.accessToken()),
			() -> assertNotNull(signUpResponse.refreshToken()),
			() -> {
				Member member = memberRepository.findByEmail(email).orElse(null);
				assertNotNull(member);
				assertEquals(email, member.getEmail());
			}
		);
	}

	@Test
	@DisplayName("이미 가입되어있는 이메일로 회원가입 시 이메일 중복 예외를 발생시킨다.")
	void Given_ValidEmailAndPassword_When_SignUp_Then_ThrowPreExistEmailException() {
		//given
		String preExistEmail = "kim@naver.com";
		getTestPersistenceMember(null, TEST_PASSWORD, preExistEmail, MemberState.BEFORE_RESEARCH);

		SignUpRequest signUpRequest = new SignUpRequest(preExistEmail, TEST_PASSWORD);
		//when, then
		assertEquals(MemberErrorInfo.PRE_EXIST_EMAIL,
			assertThrows(BusinessException.class, () -> authService.signUp(signUpRequest)).getInfo());
	}

	@Test
	@DisplayName("이메일과 비밀번호를 입력하여 로그인을 진행한다.")
	void Given_ValidEmailAndPassword_When_SignIn_Then_Success() {
		//given
		Member preSignUpMember = getTestPersistenceMember(null, TEST_PASSWORD, "t6est@naver.com",
			MemberState.BEFORE_RESEARCH);
		SignInRequest signInRequest = new SignInRequest(preSignUpMember.getEmail(), preSignUpMember.getPassword());
		//when
		SignInResponse signInResponse = authService.signIn(signInRequest.getMemberEmailAndPasswordAuthentication());
		//then
		assertAll(
			() -> assertNotNull(signInResponse.accessToken()),
			() -> assertNotNull(signInResponse.refreshToken()),
			() -> assertEquals(GRANT_TYPE, signInResponse.grantType())
		);
	}

	private Member getTestPersistenceMember(String nickname, String rawPassword, String email, MemberState state) {
		String encodePassword = passwordEncoder.encode(rawPassword);
		Member member = MemberTestDummy.createMember(nickname, encodePassword, email, state);
		return memberRepository.save(member);
	}
}
