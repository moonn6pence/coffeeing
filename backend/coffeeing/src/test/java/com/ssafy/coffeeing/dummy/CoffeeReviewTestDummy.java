package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeReview;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Profile("test")
public class CoffeeReviewTestDummy {

    public static List<CoffeeReview> createMockCoffeeReviews(Coffee coffee, List<Member> members) {

        List<CoffeeReview> reviews = new ArrayList<>();

        for (Member member : members) {
            reviews.add(createMockCoffeeReview(coffee, member, 4.0));
        }

        return reviews;
    }

    public static CoffeeReview createMockCoffeeReview(Coffee coffee, Member member, Double score) {

        return CoffeeReview.builder()
                .coffee(coffee)
                .member(member)
                .content("content")
                .score(score)
                .build();
    }
}
