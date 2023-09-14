package com.ssafy.coffeeing.modules.product.repository;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;

import java.util.List;

public interface CapsuleReviewQueryRepository {
    List<CapsuleReview> findOthersCapsuleReviews(Capsule capsule, Member member);

}
