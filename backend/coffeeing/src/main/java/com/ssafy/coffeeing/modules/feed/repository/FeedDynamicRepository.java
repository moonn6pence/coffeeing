package com.ssafy.coffeeing.modules.feed.repository;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.member.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FeedDynamicRepository {
    Slice<Feed> findFeedsByMemberAndPage(Member member, Long cursor, Pageable pageable);

    Slice<Feed> findOtherFeedsByMemberAndPage(Member owner, Member viewer, Long cursor, Pageable pageable);
}
