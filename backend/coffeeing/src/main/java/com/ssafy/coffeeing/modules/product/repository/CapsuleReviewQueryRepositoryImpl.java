package com.ssafy.coffeeing.modules.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CapsuleReviewQueryRepositoryImpl implements CapsuleReviewQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CapsuleReview> findOthersCapsuleReviews(Capsule capsule, Member member) {
        return null;
    }
}
