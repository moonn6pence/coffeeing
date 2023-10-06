package com.ssafy.coffeeing.modules.member.dto;

public record ExperienceInfoResponse(
        int experience,
        int memberLevel,
        int experienceForLevelUp
) {
}
