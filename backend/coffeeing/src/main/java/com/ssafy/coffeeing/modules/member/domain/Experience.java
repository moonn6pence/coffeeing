package com.ssafy.coffeeing.modules.member.domain;

public enum Experience {
    ADDITIONAL_EXPERIENCE_PER_LEVEL(75),
    INITIAL_EXPERIENCE(125);

    public final int exp;

    Experience(int exp){
        this.exp=exp;
    }

}
