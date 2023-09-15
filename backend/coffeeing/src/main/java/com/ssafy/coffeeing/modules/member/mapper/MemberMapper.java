package com.ssafy.coffeeing.modules.member.mapper;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.dto.BaseInfoResponse;
import com.ssafy.coffeeing.modules.member.dto.ExperienceInfoResponse;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public static BaseInfoResponse supplyBaseInfoResponseFrom(Member member) {
        return new BaseInfoResponse(
                member.getNickname(),
                member.getProfileImage()
        );
    }

    public static ExperienceInfoResponse supplyExperienceInfoResponse(Member member, int experienceForLevelUp) {
        return new ExperienceInfoResponse(
                member.getExperience(),
                member.getMemberLevel(),
                experienceForLevelUp
        );
    }

}
