package com.ssafy.coffeeing.modules.event.listener;

import com.ssafy.coffeeing.modules.event.eventer.EventRecord;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
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

    private final MemberRepository memberRepository;

    @TransactionalEventListener(phase= TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addExperience(final EventRecord eventRecord){
        log.info("경험치 추가");
        Member member = memberRepository.findById(eventRecord.memberId()).orElseThrow();

    }
}
