package com.ssafy.coffeeing.modules.auth.service;

import com.ssafy.coffeeing.modules.auth.domain.OAuth2Member;
import com.ssafy.coffeeing.modules.auth.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.domain.MemberState;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final MemberRepository memberRepository;
	private final JWTUtils jwtUtils;
	private final String GRANT_TYPE;
	public CustomOAuth2MemberService(
			@Autowired final MemberRepository memberRepository,
			@Autowired final JWTUtils jwtUtils,
			@Value("${jwt.grant-type}") final String grantType) {
		this.memberRepository = memberRepository;
		this.jwtUtils = jwtUtils;
		this.GRANT_TYPE = grantType;
	}

	@Override
	@Transactional
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String oauthType = userRequest.getClientRegistration().getRegistrationId();
		String oauthIdentifier = null;
		if(oauthType.equalsIgnoreCase("GOOGLE")) {
			oauthIdentifier = oAuth2User.getAttribute("sub");
		}

		Member member = memberRepository.findByEmail(oauthIdentifier).orElse(null);
		if(member == null) {
			member = Member.builder()
					.email(oauthIdentifier)
					.password("{noop}")
					.oauthIdentifier(oauthIdentifier)
					.state(MemberState.BEFORE_ADDITIONAL_DATA)
					.build();
			 memberRepository.save(member);
		}

		String accessToken = jwtUtils.generateAccessToken(new UsernamePasswordAuthenticationToken(oauthIdentifier, null));
		String refreshToken = jwtUtils.generateRefreshToken();
		return new OAuth2Member(oauthIdentifier, accessToken, refreshToken, GRANT_TYPE);
	}
}
