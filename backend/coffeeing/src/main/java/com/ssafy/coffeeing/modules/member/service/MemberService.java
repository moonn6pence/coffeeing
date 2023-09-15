package com.ssafy.coffeeing.modules.member.service;

import com.ssafy.coffeeing.modules.event.eventer.ActivityConductedEvent;
import com.ssafy.coffeeing.modules.member.util.MemberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.MemberErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.domain.MemberState;
import com.ssafy.coffeeing.modules.member.dto.ExistNickNameResponse;
import com.ssafy.coffeeing.modules.member.dto.OnboardRequest;
import com.ssafy.coffeeing.modules.member.dto.OnboardResponse;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final SecurityContextUtils securityContextUtils;
    private final MemberUtil memberUtil;

    @Transactional(readOnly = true)
    public ExistNickNameResponse checkDuplicateNickname(final String nickname) {
        return new ExistNickNameResponse(memberRepository.existsByNickname(nickname));
    }

    @Transactional
    public OnboardResponse insertAdditionalMemberInfo(final OnboardRequest onboardRequest) {
        if (checkDuplicateNickname(onboardRequest.nickname()).exist()) {
            throw new BusinessException(MemberErrorInfo.PRE_EXIST_NICKNAME);
        }
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();
        member.updateMemberState(MemberState.BEFORE_RESEARCH);
        member.updateByOnboardResult(onboardRequest.nickname(), onboardRequest.ageIdx(), onboardRequest.genderIdx());
        return new OnboardResponse(member.getId(), member.getNickname());
    }


    public void addExperience(final ActivityConductedEvent eventRecord) {
        Member member = memberRepository.findById(eventRecord.memberId()).orElseThrow();
        member.addExperience(eventRecord.experience());
        while (isLevelUp(member.getMemberLevel(), member.getExperience())) {
            member.subtractExperience(memberUtil.calculateLevelUpExperience(member.getMemberLevel()));
            member.levelUp();
        }
        memberRepository.save(member);
    }


    private boolean isLevelUp(int level, int experience) {
        return experience >= memberUtil.calculateLevelUpExperience(level);
    }
}
