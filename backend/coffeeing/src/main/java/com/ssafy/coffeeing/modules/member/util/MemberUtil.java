package com.ssafy.coffeeing.modules.member.util;

import com.ssafy.coffeeing.modules.member.domain.Experience;
import org.springframework.stereotype.Component;

@Component
public class MemberUtil {
    // An+1 = An + (125  + 75*n)
    private static final int[] EXPERIENCE_LIMIT = {0,125,325,600};
    private static final int LEVEL_LIMIT = 3;
    public int calculateLevelUpExperience(int level){
//        return level * Experience.ADDITIONAL_EXPERIENCE_PER_LEVEL.getValue() + Experience.INITIAL_EXPERIENCE.getValue();
        if(level==LEVEL_LIMIT){
            return EXPERIENCE_LIMIT[LEVEL_LIMIT];
        }
        return EXPERIENCE_LIMIT[level+1];
    }
}
