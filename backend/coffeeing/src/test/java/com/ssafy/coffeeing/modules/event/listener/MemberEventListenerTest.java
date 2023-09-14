package com.ssafy.coffeeing.modules.event.listener;

import com.ssafy.coffeeing.dummy.MemberTestDummy;
import com.ssafy.coffeeing.modules.event.eventer.ActivityConductedEvent;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.member.service.MemberService;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RecordApplicationEvents
public class MemberEventListenerTest extends ServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ApplicationEventPublisher pub;

    @Autowired
    ApplicationEvents events;

    @Autowired
    private MemberService memberService;

    @DisplayName("점수 증가 이벤트 발생시 점수를 증가시킨다.")
    @Test
    void Given_ExperienceRecord_When_AddExperienceEvent_Then_Success(){
        // Given
        Member member  = MemberTestDummy.createGeneralMember("닉네이무","1234","how@and.why");

        memberRepository.save(member);

        // When
        memberService.addExperience(new ActivityConductedEvent(75, member.getId()));
        System.out.println("WHY "+member.getExperience());
        //then
        assertThat(member.getExperience()).isEqualTo(75);

        // verify

    }


}
