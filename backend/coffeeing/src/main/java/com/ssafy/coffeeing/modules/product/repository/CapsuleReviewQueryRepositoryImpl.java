package com.ssafy.coffeeing.modules.product.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;
import com.ssafy.coffeeing.modules.util.RandomUtil;
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
    private final RandomUtil randomUtil;

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

    @Override
    public Capsule findRandomHighScoredCapsule(Member member) {

        Integer count = jpaQueryFactory
                .select(capsuleReview.count())
                .from(capsuleReview)
                .where(
                        capsuleReview.member.eq(member),
                        capsuleReview.score.goe(3.5)
                ).fetchFirst().intValue();

        if (count == 0) {
            return null;
        }

        return jpaQueryFactory
                .select(capsuleReview.capsule)
                .from(capsuleReview)
                .where(
                        capsuleReview.member.eq(member),
                        capsuleReview.score.goe(3.5)
                )
                .offset(randomUtil.generate(count))
                .fetchFirst();
    }

    private Long getPageCount(Capsule capsule, Member member) {

        return jpaQueryFactory
                .select(capsuleReview.count())
                .from(capsuleReview)
                .where(
                        capsuleReview.capsule.eq(capsule),
                        neMember(member)
                ).fetchOne();
    }

    private BooleanExpression neMember(Member member) {
        if (member == null) return null;

        return capsuleReview.member.ne(member);
    }
}
