package com.ssafy.coffeeing.modules.feed.mapper;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.domain.FeedLike;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedResponse;
import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.member.domain.Member;

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
