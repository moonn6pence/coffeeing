package com.ssafy.coffeeing.modules.member.dto;

public record BaseInfoResponse(
    String nickname,
    String profileImage
    // TODO : add Preference information about a member.
    // This response is used to fill information at member section

) {
}
