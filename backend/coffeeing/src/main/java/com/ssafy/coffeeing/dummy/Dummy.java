package com.ssafy.coffeeing.dummy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
@Profile("dev")
public class Dummy implements CommandLineRunner {

    private final EntityManager em;
    private final CapsuleDummy capsuleDummy;

    private void flushAndClear(){
        em.flush();
        em.clear();
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("dummy insertion start");
        
        // dummy 객체 생성 및 저장
        capsuleDummy.create5NespressoCapsules();

        flushAndClear();
        
        log.info("dummy insertion finished");
    }
}
