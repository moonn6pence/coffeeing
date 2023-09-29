package com.ssafy.coffeeing.modules.member.mapper;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.dto.ExperienceInfoResponse;
import com.ssafy.coffeeing.modules.member.dto.MemberInfoResponse;
import com.ssafy.coffeeing.modules.member.dto.MyInfoResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static MyInfoResponse supplyMyInfoResponseOf(Member member) {
        return new MyInfoResponse(
                member.getId(),
                member.getState(),
                member.getNickname(),
                member.getProfileImage()
        );
    }

}
