package com.ssafy.coffeeing.modules.feed.mapper;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.domain.FeedLike;
import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.member.domain.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedLikeMapper {

    public static ToggleResponse supplyFeedLikeResponseBy(Boolean result) {
        return new ToggleResponse(result);
    }

    public static FeedLike supplyFeedLikeEntityBy(Feed feed, Member member) {
        return FeedLike.builder()
                .member(member)
                .feed(feed)
                .build();
    }
}
