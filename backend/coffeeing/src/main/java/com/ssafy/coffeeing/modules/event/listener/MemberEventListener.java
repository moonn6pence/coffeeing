package com.ssafy.coffeeing.modules.event.listener;

import com.ssafy.coffeeing.modules.event.eventer.ExperienceEvent;
import com.ssafy.coffeeing.modules.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class MemberEventListener {

    private final MemberService memberService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    // When testing this EventListener method, count how many times it was called
    public void addExperience(final ExperienceEvent event) {
        log.info("ADDED Experience = {} FOR Member = {}",event.experience(),event.memberId());
        memberService.addExperience(event);
    }


}
