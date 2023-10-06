package com.ssafy.coffeeing.modules.global.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.MemberErrorInfo;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityContextUtils {

	private final MemberRepository memberRepository;

	/**
	 * 토큰이 반드시 필요한 요청에서, 현재 로그인한 유저의 정보를 조회합니다.
	 * @return The authenticated Member.
	 * @throws BusinessException if the member not found or authentication null
	 * */
	public Member getCurrnetAuthenticatedMember() {
		return getMemberBySecurityContext();
	}

	/**
	 * 조건부로 토큰이 필요한 API에서 만약 로그인한 유저의 접근이라면 ID값을 return 합니다.
	 * @return null or memberId
	 * */
	public Member getMemberIdByTokenOptionalRequest() {
		try {
			return getMemberBySecurityContext();
		} catch(BusinessException e) {
			return null;
		}
	}

	private Member getMemberBySecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		return memberRepository.findByEmail(email).orElseThrow(()->new BusinessException(MemberErrorInfo.NOT_FOUND));
	}
}
