package com.ssafy.coffeeing.modules.member.dto;

import com.ssafy.coffeeing.modules.member.domain.MemberState;

public record MemberInfoResponse(
    String nickname,
    String profileImage
    // TODO : add Preference information about a member.
    // This response is used to fill information at member section

) {
}
