package com.ssafy.coffeeing.modules.feed.repository;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.domain.FeedLike;
import com.ssafy.coffeeing.modules.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {

    Optional<FeedLike> findFeedLikeByFeedAndMember(Feed feed, Member member);

    @Query("select f from FeedLike f " +
            "join fetch f.member " +
            "join fetch f.feed " +
            "where f.feed in :feeds and f.member = :viewer")
    List<FeedLike> findFeedLikesByFeedsAndMember(List<Feed> feeds, Member viewer);
}
