package com.ssafy.coffeeing.modules.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.domain.MemberState;
import com.ssafy.coffeeing.modules.member.dto.ExistNickNameResponse;
import com.ssafy.coffeeing.modules.member.dto.OnboardRequest;
import com.ssafy.coffeeing.modules.member.dto.OnboardResponse;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final SecurityContextUtils securityContextUtils;

	@Transactional(readOnly = true)
	public ExistNickNameResponse checkDuplicateNickname(final String nickname) {
		return new ExistNickNameResponse(memberRepository.existsByNickname(nickname));
	}

	@Transactional
	public OnboardResponse insertAdditionalMemberInfo(final OnboardRequest onboardRequest) {
		Member member = securityContextUtils.getCurrnetAuthenticatedMember();
		member.updateMemberState(MemberState.BEFORE_RESEARCH);
		member.updateByOnboardResult(onboardRequest.nickname(), onboardRequest.ageIdx(), onboardRequest.genderIdx());
		return new OnboardResponse(member.getId(),member.getNickname());
	}
}
