package com.ssafy.coffeeing.modules.member.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

public record OnboardRequest(
	@Length(min = 1, max = 11) String nickname,
	@Min(0) @Max(5) int ageIdx,
	@Min(0) @Max(1) int genderIdx
) {
}
