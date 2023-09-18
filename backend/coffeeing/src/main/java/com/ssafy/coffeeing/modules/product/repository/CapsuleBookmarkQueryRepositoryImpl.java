package com.ssafy.coffeeing.modules.product.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.dto.BookmarkedElement;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.coffeeing.modules.product.domain.QCapsule.capsule;
import static com.ssafy.coffeeing.modules.product.domain.QCapsuleBookmark.capsuleBookmark;
import static com.ssafy.coffeeing.modules.product.domain.QCoffeeBookmark.coffeeBookmark;

@Repository
@RequiredArgsConstructor
public class CapsuleBookmarkQueryRepositoryImpl implements CapsuleBookmarkQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<BookmarkedElement> findBookmarkedCapsuleElements(Member member, Pageable pageable) {
        List<Tuple> queryResult = jpaQueryFactory
                .select(
                        capsule.id,
                        capsule.capsuleName,
                        capsule.brandKr,
                        capsule.imageUrl,
                        coffeeBookmark.id
                )
                .from(capsuleBookmark)
                .innerJoin(capsuleBookmark.capsule).fetchJoin()
                .where(
                        capsuleBookmark.member.eq(member)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(capsuleBookmark.id.desc())
                .fetch();

        List<BookmarkedElement> bookmarks = queryResult
                .stream()
                .map((item) -> ProductMapper.supplyBookmarkedCoffeeElementOf(
                                item.get(capsule.id),
                                item.get(capsule.capsuleName),
                                item.get(capsule.brandKr),
                                item.get(capsule.imageUrl),
                                item.get(capsuleBookmark.id)
                        )
                )
                .toList();

        return new PageImpl<>(bookmarks, pageable, getCount(member));
    }

    private Long getCount(Member member) {
        return jpaQueryFactory
                .select(coffeeBookmark.count())
                .from(coffeeBookmark)
                .where(coffeeBookmark.member.eq(member))
                .fetchOne();
    }


}
