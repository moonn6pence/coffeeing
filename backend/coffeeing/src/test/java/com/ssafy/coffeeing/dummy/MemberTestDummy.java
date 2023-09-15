package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.member.domain.Age;
import com.ssafy.coffeeing.modules.member.domain.Gender;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.domain.MemberState;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Profile("test")
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

    public static List<Member> create25GeneralMembers() {

        List<Member> members = new ArrayList<>();

        for(int i = 0 ; i < 25; i++) {
            members.add(createMember("nickname" + i, "pwd", "email" + i + "@naver.com", MemberState.NORMAL));
        }

        return members;
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
