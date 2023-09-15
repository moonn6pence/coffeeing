package com.ssafy.coffeeing.modules.member.domain;

public enum Experience {
    ADDITIONAL_EXPERIENCE_PER_LEVEL(75),
    INITIAL_EXPERIENCE(125);

    private final int exp;

    Experience(int exp){
        this.exp=exp;
    }

    public int getValue(){
        return this.exp;
    }


}
