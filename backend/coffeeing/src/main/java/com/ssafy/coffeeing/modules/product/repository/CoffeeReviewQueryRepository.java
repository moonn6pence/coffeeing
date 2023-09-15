package com.ssafy.coffeeing.modules.product.repository;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CoffeeReviewQueryRepository {

    Page<CoffeeReview> findOthersCoffeeReviews(Coffee coffee, Member member, Pageable pageable);
}
