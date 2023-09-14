package com.ssafy.coffeeing.modules.product.repository;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CapsuleReviewQueryRepository {
    Page<CapsuleReview> findOthersCapsuleReviews(Capsule capsule, Member member, Pageable pageable);

}
