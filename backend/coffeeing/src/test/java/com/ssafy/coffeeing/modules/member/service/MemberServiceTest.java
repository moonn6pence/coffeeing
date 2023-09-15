package com.ssafy.coffeeing.modules.member.service;

import com.ssafy.coffeeing.dummy.MemberTestDummy;
import com.ssafy.coffeeing.modules.event.eventer.ActivityConductedEvent;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RecordApplicationEvents
public class MemberServiceTest extends ServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    ApplicationEvents events;

    @Autowired
    private MemberService memberService;

    @DisplayName("점수 증가시 저장된 점수가 증가한다.")
    @Test
    void Given_ExperienceRecord_When_AddExperienceEvent_Then_Success(){
        // given
        Member member  = MemberTestDummy.createGeneralMember("닉네이무","1234","how@and.why");
        memberRepository.save(member);
        // when
        memberService.addExperience(new ActivityConductedEvent(75, member.getId()));
        // then
        assertThat(member.getExperience()).isEqualTo(75);

    }

    @DisplayName("점수 증가시 레벨업을 실행하고 점수를 감소시킨다.")
    @Test
    void Given_ActivityConductedEvent_When_AddExperienceEvent_Then_Success(){
        // given
        Member member = MemberTestDummy.createGeneralMember("얍","1234","a@a.com");
        memberRepository.save(member);
        // when
        memberService.addExperience(new ActivityConductedEvent(150,member.getId()));
        // then
        assertAll(
                ()->assertThat(member.getExperience()).isEqualTo(25),
                ()->assertThat(member.getLevel()).isEqualTo(1)
        );
    }


}
