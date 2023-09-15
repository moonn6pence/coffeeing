package com.ssafy.coffeeing.modules.feed.mapper;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.dto.FeedProjection;
import com.ssafy.coffeeing.modules.feed.dto.ProfileFeedsResponse;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedResponse;
import com.ssafy.coffeeing.modules.member.domain.Member;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
public class FeedMapper {

    public static UploadFeedResponse supplyFeedResponseBy(Feed feed) {
        return new UploadFeedResponse(feed.getId());
    }

    public static Feed supplyFeedEntityBy(Member member, String content, String imageUrl) {
        return Feed.builder()
                .member(member)
                .content(content)
                .imageUrl(imageUrl)
                .likeCount(0L)
                .build();
    }

    public static ProfileFeedsResponse supplyFeedEntityOf(Slice<FeedProjection> feeds, Long nextCursor) {
        return new ProfileFeedsResponse(feeds.getContent(), feeds.hasNext(), nextCursor);
    }
}
