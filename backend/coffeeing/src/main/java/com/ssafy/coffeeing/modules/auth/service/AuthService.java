package com.ssafy.coffeeing.modules.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.coffeeing.modules.auth.domain.RefreshToken;
import com.ssafy.coffeeing.modules.auth.dto.ReissueRequest;
import com.ssafy.coffeeing.modules.auth.dto.ReissueResponse;
import com.ssafy.coffeeing.modules.auth.dto.SignInResponse;
import com.ssafy.coffeeing.modules.auth.dto.SignUpRequest;
import com.ssafy.coffeeing.modules.auth.dto.SignUpResponse;
import com.ssafy.coffeeing.modules.auth.repository.RefreshTokenRepository;
import com.ssafy.coffeeing.modules.auth.util.JWTUtils;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.AuthErrorInfo;
import com.ssafy.coffeeing.modules.global.exception.info.MemberErrorInfo;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.domain.MemberState;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;

@Service

public class AuthService {

	private final JWTUtils jwtUtils;
	private final String GRANT_TYPE;

	private final MemberRepository memberRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final PasswordEncoder passwordEncoder;


	public AuthService(@Autowired final JWTUtils jwtUtils,
					   @Autowired final MemberRepository memberRepository,
					   @Autowired final RefreshTokenRepository refreshTokenRepository,
					   @Autowired final PasswordEncoder passwordEncoder,
					   @Value("${jwt.grant-type}") final String grantType) {

		this.jwtUtils = jwtUtils;
		this.memberRepository = memberRepository;
		this.refreshTokenRepository = refreshTokenRepository;
		this.passwordEncoder = passwordEncoder;
		this.GRANT_TYPE = grantType;
	}

	public SignInResponse signIn(Authentication authentication) {
		String accessToken = jwtUtils.generateAccessToken(authentication);
		String refreshToken = jwtUtils.generateRefreshToken();

		refreshTokenRepository.save(new RefreshToken(authentication.getName(), refreshToken));
		return new SignInResponse(accessToken, refreshToken, GRANT_TYPE);
	}

	@Transactional
	public SignUpResponse signUp(SignUpRequest signUpRequest) {
		if(memberRepository.existsByEmail(signUpRequest.email())) {
			throw new BusinessException(MemberErrorInfo.PRE_EXIST_EMAIL);
		}

		Member member = Member.builder()
			.email(signUpRequest.email())
			.password(passwordEncoder.encode(signUpRequest.password()))
			.state(MemberState.BEFORE_ADDITIONAL_DATA)
			.build();

		memberRepository.save(member);

		String accessToken = jwtUtils.generateAccessToken(new UsernamePasswordAuthenticationToken(member.getEmail(), null));
		String refreshToken = jwtUtils.generateRefreshToken();
		refreshTokenRepository.save(new RefreshToken(signUpRequest.email(), refreshToken));
		return new SignUpResponse(member.getId(), member.getEmail(), accessToken, refreshToken, GRANT_TYPE);
	}

	@Transactional(readOnly = true)
	public ReissueResponse reissueAccessToken(ReissueRequest reissueRequest) {
		RefreshToken refreshToken = refreshTokenRepository.findByToken(reissueRequest.refreshToken())
			.orElseThrow(() -> new BusinessException(AuthErrorInfo.NOT_VALID_TOKEN));

		String email = refreshToken.getEmail();
		String reissueToken = jwtUtils.generateAccessToken(new UsernamePasswordAuthenticationToken(email, null));
		return new ReissueResponse(email, reissueToken, GRANT_TYPE);
	}
}
