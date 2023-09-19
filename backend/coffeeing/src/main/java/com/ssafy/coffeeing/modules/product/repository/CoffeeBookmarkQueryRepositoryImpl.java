package com.ssafy.coffeeing.modules.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.dto.BookmarkedElement;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.coffeeing.modules.product.domain.QCoffeeBookmark.coffeeBookmark;

@Repository
@RequiredArgsConstructor
public class CoffeeBookmarkQueryRepositoryImpl implements CoffeeBookmarkQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<BookmarkedElement> findBookmarkedCoffeeElements(Member member, Pageable pageable) {
        List<Coffee> queryResult = jpaQueryFactory
                .select(
                        coffeeBookmark.coffee
                )
                .from(coffeeBookmark)
                .innerJoin(coffeeBookmark.coffee)
                .where(
                        coffeeBookmark.member.eq(member)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(coffeeBookmark.id.desc())
                .fetch();

        List<BookmarkedElement> bookmarks = queryResult
                .stream()
                .map((item) -> ProductMapper.supplyBookmarkedElementOf(
                                item.getId(),
                                item.getCoffeeName(),
                                item.getRegion(),
                                item.getImageUrl()
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
