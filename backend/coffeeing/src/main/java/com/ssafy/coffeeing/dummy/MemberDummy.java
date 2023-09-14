package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.member.domain.Age;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.domain.MemberState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemberDummy {

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
            add("test1");
            add("test2");
            add("test3");
            add("test4");
            add("test5");
        }
    };


    public List<Member> createMemberDummies() {
        List<Member> members = new ArrayList<>();

        for(int i = 0; i < nicknames.size(); i++) {
            members.add(Member.builder()
                    .nickname(nicknames.get(i))
                    .password(passwords.get(i))
                    .state(MemberState.NORMAL)
                    .age(Age.TWENTY)
                    .profileImage(null)
                    .oauthIdentifier(null)
                    .state(MemberState.NORMAL)
                    .build());
        }
        return members;
    }
}
