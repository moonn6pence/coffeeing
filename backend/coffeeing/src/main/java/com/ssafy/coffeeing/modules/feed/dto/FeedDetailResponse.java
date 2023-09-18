package com.ssafy.coffeeing.modules.feed.dto;

import java.util.List;

public record FeedDetailResponse(
        Long id,
        List<ImageElement> images,
        String content,
        Long registerId,
        Long likeCount,
        String registerName,
        String registerProfileImg,
        Boolean isLike,
        Boolean isMine
) {
}
