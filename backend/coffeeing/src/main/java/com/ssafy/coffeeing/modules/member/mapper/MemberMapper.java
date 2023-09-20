package com.ssafy.coffeeing.modules.member.mapper;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.dto.MemberInfoResponse;
import com.ssafy.coffeeing.modules.member.dto.ExperienceInfoResponse;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public static MemberInfoResponse supplyBaseInfoResponseFrom(Member member) {
        return new MemberInfoResponse(
                member.getNickname(),
                member.getProfileImage()
        );
    }

    public static ExperienceInfoResponse supplyExperienceInfoResponseOf(Member member, int experienceForLevelUp) {
        return new ExperienceInfoResponse(
                member.getExperience(),
                member.getMemberLevel(),
                experienceForLevelUp
        );
    }

}
