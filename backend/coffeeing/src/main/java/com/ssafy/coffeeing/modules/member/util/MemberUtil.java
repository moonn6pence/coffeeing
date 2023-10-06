package com.ssafy.coffeeing.modules.member.util;

import org.springframework.stereotype.Component;

@Component
public class MemberUtil {
    // An+1 = An + (125  + 75*n)
    private static final int[] EXPERIENCE_LIMIT = {0,125,325,600};
    private static final int LEVEL_LIMIT = 3;

    /**
     * 아래의 재귀식을 미리 연산하여 사용한다.
     * 수식 : An+1 = An + level * Experience.ADDITIONAL_EXPERIENCE_PER_LEVEL.getValue() + Experience.INITIAL_EXPERIENCE.getValue();
     */
    public int calculateLevelUpExperience(int level){
        if(level==LEVEL_LIMIT){
            return EXPERIENCE_LIMIT[LEVEL_LIMIT];
        }
        return EXPERIENCE_LIMIT[level+1];
    }
}
