package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.member.domain.Age;
import com.ssafy.coffeeing.modules.member.domain.Gender;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.domain.MemberState;

public class MemberTestDummy {

    public static Member createGeneralMember() {
        return Member.builder()
                .email("test1234@naver.com")
                .age(Age.TEENAGER)
                .gender(Gender.MEN)
                .nickname("testNickname")
                .password("testPassword")
                .state(MemberState.NORMAL)
                .oauthIdentifier(null)
                .profileImage(null)
                .build();
    }

    public static Member createBeforeResearchMember() {
        return Member.builder()
                .email("test@naver.com")
                .age(Age.TWENTY)
                .gender(Gender.WOMEN)
                .nickname("testNickname1")
                .password("testPassword1")
                .state(MemberState.BEFORE_RESEARCH)
                .oauthIdentifier(null)
                .profileImage(null)
                .build();
    }
}
