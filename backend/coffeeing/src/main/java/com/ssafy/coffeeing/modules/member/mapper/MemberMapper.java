package com.ssafy.coffeeing.modules.member.mapper;

import com.ssafy.coffeeing.modules.global.embedded.CoffeeCriteria;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.dto.ExperienceInfoResponse;
import com.ssafy.coffeeing.modules.member.dto.MemberInfoResponse;
import com.ssafy.coffeeing.modules.member.dto.MyInfoResponse;
import com.ssafy.coffeeing.modules.survey.domain.Preference;
import com.ssafy.coffeeing.modules.survey.dto.CoffeeCriteriaResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMapper {
  public static MemberInfoResponse supplyBaseInfoResponseOf(Member member, Preference preference) {
    return new MemberInfoResponse(
            member.getNickname(),
            member.getProfileImage(),
            supplyCoffeeCriteriaResponseFrom(preference)
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


  public static CoffeeCriteriaResponse supplyCoffeeCriteriaResponseFrom(Preference preference) {
    if (preference == null) {
      return null;
    }
    CoffeeCriteria coffeeCriteria = preference.getCoffeeCriteria();
    return new CoffeeCriteriaResponse(
            coffeeCriteria.getRoast(),
            coffeeCriteria.getAcidity(),
            coffeeCriteria.getBody()
    );
  }

}
