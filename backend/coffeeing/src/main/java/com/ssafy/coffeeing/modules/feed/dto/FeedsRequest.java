package com.ssafy.coffeeing.modules.feed.dto;


import lombok.Builder;

import javax.validation.constraints.Positive;

public record FeedsRequest(
        Long cursor,

        @Positive(message = "유효하지 않은 페이지 크기입니다.")
        Integer size
) {
        @Builder
        public FeedsRequest {
                cursor = null;
                size = 10;
        }
}
