package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;
import com.ssafy.coffeeing.modules.product.repository.CapsuleReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile("dev")
@RequiredArgsConstructor
@Component
public class CapsuleReviewDummy {

    private final CapsuleReviewRepository capsuleReviewRepository;

    public List<CapsuleReview> createCapsuleReviewOfCapsulesAndMembers(List<Capsule> capsules, List<Member> members) {

        List<CapsuleReview> capsuleReviews = new ArrayList<>();

        for(Capsule capsule : capsules) {
            for (Member member : members) {
                capsuleReviews.add(createCapsuleReview(capsule, member));
            }
        }


        return capsuleReviewRepository.saveAll(capsuleReviews);
    }

    private CapsuleReview createCapsuleReview(Capsule capsule, Member member) {
        return CapsuleReview.builder()
                .capsule(capsule)
                .member(member)
                .content("taste very good")
                .score(4.0)
                .build();
    }
}
