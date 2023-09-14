package com.ssafy.coffeeing.modules.member.util;

import com.ssafy.coffeeing.modules.member.domain.Experience;
import org.springframework.stereotype.Component;

@Component
public class MemberUtil {
    public int calculateLevelUpExperience(int level){
        return level * Experience.ADDITIONAL_EXPERIENCE_PER_LEVEL.exp + Experience.INITIAL_EXPERIENCE.exp;
    }
}
