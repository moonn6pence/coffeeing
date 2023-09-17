package com.ssafy.coffeeing.modules.feed.repository;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.dto.FeedProjection;
import com.ssafy.coffeeing.modules.member.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FeedDynamicRepository {
    Slice<FeedProjection> findFeedsByMemberAndPage(Member owner, Long cursor, Pageable pageable);

    Slice<Feed> findFeedsByFeedPage(Long cursor, Pageable pageable);
}
