package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
@Profile("test")
public class Dummy implements CommandLineRunner {

    private final EntityManager em;
    private final CapsuleDummy capsuleDummy;
    private final CoffeeDummy coffeeDummy;
    private final FeedDummy feedDummy;
    private final MemberDummy memberDummy;

    private final CapsuleReviewDummy capsuleReviewDummy;
    private final CoffeeReviewDummy coffeeReviewDummy;

    private void flushAndClear(){
        em.flush();
        em.clear();
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("dummy insertion start");
        
        // dummy 객체 생성 및 저장
        List<Capsule> capsules = capsuleDummy.create5NespressoCapsules();
        List<Coffee> coffees = coffeeDummy.create5CoffeeDummy();
        List<Member> members = memberDummy.createMemberDummies();
        feedDummy.createFeedDummies(members);
        capsuleReviewDummy.createCapsuleReviewOfCapsulesAndMembers(capsules, members);
        coffeeReviewDummy.createCoffeeReviewOfCoffeesAndMembers(coffees, members);

        flushAndClear();
        
        log.info("dummy insertion finished");
    }
}
