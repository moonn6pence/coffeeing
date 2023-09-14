package com.ssafy.coffeeing.modules.product.repository;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeReviewRepository extends JpaRepository<CoffeeReview, Long> {
    CoffeeReview findCoffeeReviewByCoffeeAndMember(Coffee coffee, Member member);
}
