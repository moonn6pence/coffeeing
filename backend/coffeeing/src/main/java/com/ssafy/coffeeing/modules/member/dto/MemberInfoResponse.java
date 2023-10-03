package com.ssafy.coffeeing.modules.member.dto;

import com.ssafy.coffeeing.modules.survey.dto.CoffeeCriteriaResponse;

public record MemberInfoResponse(
    String nickname,
    String profileImage,
    CoffeeCriteriaResponse preference
) {
}
