package com.ssafy.coffeeing.modules.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.dto.SimpleProductElement;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.coffeeing.modules.product.domain.QCapsuleBookmark.capsuleBookmark;
import static com.ssafy.coffeeing.modules.product.domain.QCoffeeBookmark.coffeeBookmark;

@Repository
@RequiredArgsConstructor
public class CapsuleBookmarkQueryRepositoryImpl implements CapsuleBookmarkQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<SimpleProductElement> findBookmarkedCapsuleElements(Member member, Pageable pageable) {
        List<Capsule> queryResult = jpaQueryFactory
                .select(
                        capsuleBookmark.capsule
                )
                .from(capsuleBookmark)
                .innerJoin(capsuleBookmark.capsule)
                .innerJoin(capsuleBookmark.member)
                .where(
                        capsuleBookmark.member.eq(member)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(capsuleBookmark.id.desc())
                .fetch();

        List<SimpleProductElement> simpleProductElements = queryResult
                .stream()
                .map((item) -> ProductMapper.supplySimpleProductElementOf(
                                item.getId(),
                                item.getBrandKr(),
                                item.getCapsuleNameKr(),
                                item.getImageUrl()
                        )
                )
                .toList();

        return new PageImpl<>(simpleProductElements, pageable, getCount(member));
    }

    private Long getCount(Member member) {
        return jpaQueryFactory
                .select(coffeeBookmark.count())
                .from(coffeeBookmark)
                .where(coffeeBookmark.member.eq(member))
                .fetchOne();
    }


}
