package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.member.domain.Age;
import com.ssafy.coffeeing.modules.member.domain.Gender;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.domain.MemberState;

public class MemberTestDummy {

    public static Member createGeneralMember(String nickname, String password, String email) {
        return createMember(nickname, password, email, MemberState.NORMAL);
    }

    public static Member createMemberSean() {
        return Member.builder()
                .email("seanbryan@gmail.com")
                .age(Age.TEENAGER)
                .gender(Gender.MEN)
                .nickname("Sean")
                .password("general")
                .state(MemberState.NORMAL)
                .oauthIdentifier(null)
                .profileImage(null)
                .build();
    }

    public static Member createBeforeResearchMember(String nickname, String password, String email) {
        return createMember(nickname, password, email, MemberState.BEFORE_RESEARCH);
    }

    public static Member createMember(String nickname, String password, String email, MemberState state) {
        return Member.builder()
            .email(email)
            .age(Age.TWENTY)
            .gender(Gender.WOMEN)
            .nickname(nickname)
            .password(password)
            .state(state)
            .oauthIdentifier(null)
            .profileImage(null)
            .build();
    }
}
