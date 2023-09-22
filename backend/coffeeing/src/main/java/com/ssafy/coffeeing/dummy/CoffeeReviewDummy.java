package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeReview;
import com.ssafy.coffeeing.modules.product.repository.CoffeeReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile("test")
@RequiredArgsConstructor
@Component
public class CoffeeReviewDummy {

    private final CoffeeReviewRepository coffeeReviewRepository;

    public List<CoffeeReview> createCoffeeReviewOfCoffeesAndMembers(List<Coffee> coffees, List<Member> members) {

        List<CoffeeReview> coffeeReviews = new ArrayList<>();

        for(Coffee capsule : coffees) {
            for (Member member : members) {
                coffeeReviews.add(createCoffeeReview(capsule, member));
            }
        }


        return coffeeReviewRepository.saveAll(coffeeReviews);
    }

    private CoffeeReview createCoffeeReview(Coffee coffee, Member member) {
        return CoffeeReview.builder()
                .coffee(coffee)
                .member(member)
                .content("taste very good")
                .score(4.0)
                .build();
    }
}
