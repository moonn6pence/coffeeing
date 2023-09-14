package com.ssafy.coffeeing.modules.product.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.coffeeing.modules.product.domain.QCapsuleReview.capsuleReview;

@Repository
@RequiredArgsConstructor
public class CapsuleReviewQueryRepositoryImpl implements CapsuleReviewQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CapsuleReview> findOthersCapsuleReviews(Capsule capsule, Member member, Pageable pageable) {

        List<CapsuleReview> reviews = jpaQueryFactory
                .select(capsuleReview)
                .from(capsuleReview)
                .innerJoin(capsuleReview.member).fetchJoin()
                .where(
                        capsuleReview.capsule.eq(capsule),
                        neMember(member)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(capsuleReview.id.desc())
                .fetch();

        Long totalCount = getPageCount(capsule, member);

        return new PageImpl<>(reviews, pageable, totalCount);
    }

    private Long getPageCount(Capsule capsule, Member member) {

        Long count = jpaQueryFactory
                .select(capsuleReview.count())
                .from(capsuleReview)
                .where(
                        capsuleReview.capsule.eq(capsule),
                        neMember(member)
                ).fetchOne();

        return count;
    }

    private BooleanExpression neMember(Member member) {
        if (member == null) return null;

        return capsuleReview.member.ne(member);
    }
}
