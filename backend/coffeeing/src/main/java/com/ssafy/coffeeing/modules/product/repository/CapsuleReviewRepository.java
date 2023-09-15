package com.ssafy.coffeeing.modules.product.repository;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapsuleReviewRepository extends JpaRepository<CapsuleReview, Long> {

    CapsuleReview findCapsuleReviewByCapsuleAndMember(Capsule capsule, Member member);
}
