package com.ssafy.coffeeing.modules.feed.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class FeedPageElement {
    Long id;
    List<ImageElement> images;
    String content;
    Long registerId;
    Long likeCount;
    String registerName;
    String registerProfileImg;
    Boolean isLike = false;
    Boolean isMine = false;

    public FeedPageElement(Long id, List<ImageElement> images, String content, Long registerId, Long likeCount,
                           String registerName, String registerProfileImg) {
        this.id = id;
        this.images = images;
        this.content = content;
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
