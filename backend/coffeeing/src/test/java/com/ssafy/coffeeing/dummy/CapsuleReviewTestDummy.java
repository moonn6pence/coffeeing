package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Profile("test")
public class CapsuleReviewTestDummy {

    public static List<CapsuleReview> createMockCapsuleReviews(Capsule capsule, List<Member> members) {

        List<CapsuleReview> reviews = new ArrayList<>();

        for (Member member : members) {
            reviews.add(createMockCapsuleReview(capsule, member));
        }

        return reviews;
    }

    private static CapsuleReview createMockCapsuleReview(Capsule capsule, Member member) {

        return CapsuleReview.builder()
                .capsule(capsule)
                .member(member)
                .content("content")
                .score(4.0)
                .build();
    }
}
