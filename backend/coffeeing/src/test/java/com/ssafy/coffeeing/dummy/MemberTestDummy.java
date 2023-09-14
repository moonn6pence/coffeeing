package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.member.domain.Age;
import com.ssafy.coffeeing.modules.member.domain.Gender;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.domain.MemberState;

public class MemberTestDummy {

    public static Member createGeneralMember(String nickname, String password, String email) {
        return Member.builder()
                .email(email)
                .age(Age.TEENAGER)
                .gender(Gender.MEN)
                .nickname(nickname)
                .password(password)
                .state(MemberState.NORMAL)
                .oauthIdentifier(null)
                .profileImage(null)
                .build();
    }

    public static Member createBeforeResearchMember(String nickname, String password, String email) {
        return Member.builder()
                .email(email)
                .age(Age.TWENTY)
                .gender(Gender.WOMEN)
                .nickname(nickname)
                .password(password)
                .state(MemberState.BEFORE_RESEARCH)
                .oauthIdentifier(null)
                .profileImage(null)
                .build();
    }
}
