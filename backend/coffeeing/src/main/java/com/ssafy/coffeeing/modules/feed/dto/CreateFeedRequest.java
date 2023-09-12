package com.ssafy.coffeeing.modules.feed.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.List;

public record CreateFeedRequest(
        List<ImageElement> images,
        String content
) {
    @JsonAnyGetter
    public List<ImageElement> getImages() {
        return images;
    }
}
