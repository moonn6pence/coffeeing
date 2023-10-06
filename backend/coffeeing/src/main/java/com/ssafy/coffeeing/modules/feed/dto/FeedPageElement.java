package com.ssafy.coffeeing.modules.feed.dto;

import com.ssafy.coffeeing.modules.search.domain.Tag;
import lombok.Getter;

import java.util.List;

@Getter
public class FeedPageElement {
    Long feedId;
    List<ImageElement> images;
    String content;
    Tag tag;
    Long registerId;
    Integer likeCount;
    String registerName;
    String registerProfileImg;
    Boolean isLike = false;
    Boolean isMine = false;

    public FeedPageElement(Long feedId, List<ImageElement> images, String content, Tag tag, Long registerId, Integer likeCount,
                           String registerName, String registerProfileImg, Boolean isLike) {
        this.feedId = feedId;
        this.images = images;
        this.content = content;
        this.tag = tag;
        this.registerId = registerId;
        this.likeCount = likeCount;
        this.registerName = registerName;
        this.registerProfileImg = registerProfileImg;
        this.isLike = isLike;
    }

    public void updateIsMineStatus() {
        this.isMine = !this.isMine;
    }
}
