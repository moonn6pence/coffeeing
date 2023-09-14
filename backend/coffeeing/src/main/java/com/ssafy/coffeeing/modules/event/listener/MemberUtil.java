package com.ssafy.coffeeing.modules.event.listener;

import com.ssafy.coffeeing.modules.member.domain.Experience;

public class MemberUtil {
    public int calculateLevelUpExperience(int level){
        return level * Experience.ADDITIONAL_EXPERIENCE_PER_LEVEL.exp + Experience.INITIAL_EXPERIENCE.exp
    }
}
