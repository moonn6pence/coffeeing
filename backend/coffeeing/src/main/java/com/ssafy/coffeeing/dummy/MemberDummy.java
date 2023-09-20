package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.member.domain.Age;
import com.ssafy.coffeeing.modules.member.domain.Gender;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.domain.MemberState;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile("dev")
@RequiredArgsConstructor
@Component
public class MemberDummy {

    private final MemberRepository memberRepository;

    private final List<String> nicknames = new ArrayList<>(){
        {
            add("james");
            add("paul");
            add("arch");
            add("sam");
            add("daniel");
        }
    };
    private final List<String> passwords = new ArrayList<>(){
        {
            add("{noop}test1");
            add("{noop}test2");
            add("{noop}test3");
            add("{noop}test4");
            add("{noop}test5");
        }
    };
    private final List<String> emails = new ArrayList<>(){
        {
            add("test1@test.com");
            add("test2@test.com");
            add("test3@test.com");
            add("test4@test.com");
            add("test5@test.com");
        }
    };

    public List<Member> createMemberDummies() {
        List<Member> members = new ArrayList<>();

        for(int i = 0; i < nicknames.size(); i++) {
            members.add(Member.builder()
                    .nickname(nicknames.get(i))
                    .password(passwords.get(i))
                    .email(emails.get(i))
                    .state(MemberState.NORMAL)
                    .gender(Gender.MEN)
                    .age(Age.TWENTY)
                    .profileImage(null)
                    .oauthIdentifier(null)
                    .state(MemberState.NORMAL)
                    .build());
        }

        memberRepository.saveAll(members);
        return members;
    }
}
