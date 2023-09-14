package com.ssafy.coffeeing.modules.event.listener;

import com.ssafy.coffeeing.modules.event.eventer.ActivityConductedEvent;
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
    public void addExperience(final ActivityConductedEvent event) { // count this
        memberService.addExperience(event);
    }


}
