package com.ssafy.coffeeing.modules.member.dto;

import com.ssafy.coffeeing.modules.member.domain.MemberState;

public record MyInfoResponse(
	Long memberId,
	MemberState state,

	String nickname,
	String profileImage
) {
}
