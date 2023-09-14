package com.ssafy.coffeeing.modules.feed.dto;

import java.util.List;

public record FeedElement(
        Long feedId,
        Long likeCount,
        List<ImageElement> images,
        String content,
        Long registerId,
        String registerName,
        String registerProfileImage,
        Boolean isLike,
        Boolean isMine
) {
}
