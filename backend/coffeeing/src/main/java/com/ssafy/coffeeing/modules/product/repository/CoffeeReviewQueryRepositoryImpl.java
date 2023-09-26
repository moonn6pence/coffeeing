package com.ssafy.coffeeing.modules.product.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeReview;
import com.ssafy.coffeeing.modules.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.coffeeing.modules.product.domain.QCoffeeReview.coffeeReview;

@Repository
@RequiredArgsConstructor
public class CoffeeReviewQueryRepositoryImpl implements CoffeeReviewQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final RandomUtil randomUtil;

    @Override
    public Page<CoffeeReview> findOthersCoffeeReviews(Coffee coffee, Member member, Pageable pageable) {

        List<CoffeeReview> reviews = jpaQueryFactory
                .select(coffeeReview)
                .from(coffeeReview)
                .innerJoin(coffeeReview.member).fetchJoin()
                .where(
                        coffeeReview.coffee.eq(coffee),
                        neMember(member)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(coffeeReview.id.desc())
                .fetch();

        Long totalCount = getPageCount(coffee, member);

        return new PageImpl<>(reviews, pageable, totalCount);
    }

    @Override
    public Coffee findRandomHighScoredCoffee(Member member) {

        Integer count = jpaQueryFactory
                .select(coffeeReview.count())
                .from(coffeeReview)
                .where(
                        coffeeReview.member.eq(member),
                        coffeeReview.score.goe(3.5)
                ).fetchFirst().intValue();

        if (count == 0) {
            return null;
        }

        return jpaQueryFactory
                .select(coffeeReview.coffee)
                .from(coffeeReview)
                .where(
                        coffeeReview.member.eq(member),
                        coffeeReview.score.goe(3.5)
                )
                .offset(randomUtil.generate(count))
                .fetchOne();
    }

    private Long getPageCount(Coffee coffee, Member member) {

        return jpaQueryFactory
                .select(coffeeReview.count())
                .from(coffeeReview)
                .where(
                        coffeeReview.coffee.eq(coffee),
                        neMember(member)
                ).fetchOne();
    }

    private BooleanExpression neMember(Member member) {
        if (member == null) return null;

        return coffeeReview.member.ne(member);
    }

}
