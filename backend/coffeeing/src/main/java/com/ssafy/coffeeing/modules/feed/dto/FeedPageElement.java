package com.ssafy.coffeeing.modules.feed.dto;

import com.ssafy.coffeeing.modules.tag.domain.Tag;
import lombok.Getter;

import java.util.List;

@Getter
public class FeedPageElement {
    Long feedId;
    List<ImageElement> images;
    String content;
    Tag tag;
    Long registerId;
    Long likeCount;
    String registerName;
    String registerProfileImg;
    Boolean isLike = false;
    Boolean isMine = false;

    public FeedPageElement(Long feedId, List<ImageElement> images, String content, Tag tag, Long registerId, Long likeCount,
                           String registerName, String registerProfileImg) {
        this.feedId = feedId;
        this.images = images;
        this.content = content;
        this.tag = tag;
        this.registerId = registerId;
        this.likeCount = likeCount;
        this.registerName = registerName;
        this.registerProfileImg = registerProfileImg;
    }

    public void updateIsMineStatus() {
        this.isMine = !this.isMine;
    }

    public void updateIsLikeStatus() {
        this.isLike = !this.isLike;
    }
}
