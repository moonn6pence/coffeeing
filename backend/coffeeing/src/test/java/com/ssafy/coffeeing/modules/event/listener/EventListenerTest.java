package com.ssafy.coffeeing.modules.event.listener;

import com.ssafy.coffeeing.modules.event.eventer.ExperienceEvent;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;


import static org.junit.jupiter.api.Assertions.*;

@RecordApplicationEvents
class EventListenerTest extends ServiceTest {

    @MockBean
    private MemberEventListener memberEventListener;

    @Autowired
    private ApplicationEvents applicationEvents;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    @DisplayName("멤버 점수 증가 이벤트를 수행")
    void Given_MemberIdWithExperience_When_LaunchExperienceEvent_Then_Success() {
        // given
        Long memberId = generalMember.getId();
        int experience = 75;
        ExperienceEvent activityConductedEvent = new ExperienceEvent(experience, memberId);
        // when
        applicationEventPublisher.publishEvent(activityConductedEvent);
        // then
        assertTrue(applicationEvents.stream(ExperienceEvent.class).allMatch(experienceEvent -> experienceEvent.experience() == experience && experienceEvent.memberId() == memberId));
        assertEquals(1, (int) applicationEvents.stream(ExperienceEvent.class).count());
    }

}
