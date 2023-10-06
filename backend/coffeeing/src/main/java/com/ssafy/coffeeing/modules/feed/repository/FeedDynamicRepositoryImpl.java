package com.ssafy.coffeeing.modules.feed.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.dto.FeedProjection;
import com.ssafy.coffeeing.modules.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.coffeeing.modules.feed.domain.QFeed.feed;

@Repository
@RequiredArgsConstructor
public class FeedDynamicRepositoryImpl implements FeedDynamicRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<FeedProjection> findFeedsByMemberAndPage(Member owner, Long cursor, Pageable pageable) {
        List<FeedProjection> feedProjections = jpaQueryFactory.select(
                        Projections.fields(FeedProjection.class, feed.id.as("feedId"), feed.imageUrl.as("images")))
                .from(feed)
                .join(feed.member)
                .where(feed.member.eq(owner), cursorId(cursor))
                .limit(pageable.getPageSize() + 1)
                .orderBy(feed.id.desc())
                .fetch();

        boolean hasNext = pageable.isPaged() && feedProjections.size() > pageable.getPageSize();

        return new SliceImpl<>(hasNext ? feedProjections.subList(0, pageable.getPageSize()) : feedProjections, pageable,
                hasNext);
    }

    @Override
    public Slice<Feed> findFeedsByFeedPage(Long cursor, Pageable pageable) {
        List<Feed> feeds = jpaQueryFactory.selectFrom(feed)
                .join(feed.member)
                .where(cursorId(cursor))
                .limit(pageable.getPageSize() + 1)
                .orderBy(feed.id.desc())
                .fetch();

        boolean hasNext = pageable.isPaged() && feeds.size() > pageable.getPageSize();

        return new SliceImpl<>(hasNext ? feeds.subList(0, pageable.getPageSize()) : feeds, pageable, hasNext);
    }

    private BooleanExpression cursorId(Long cursorId){
        return cursorId == null ? null : feed.id.lt(cursorId);
    }
}
