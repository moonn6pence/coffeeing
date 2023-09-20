package com.ssafy.coffeeing.modules.feed.dto;


import lombok.Builder;

import javax.validation.constraints.Positive;
import java.util.Objects;

public record FeedsRequest(
        Long cursor,

        @Positive(message = "유효하지 않은 페이지 크기입니다.")
        Integer size
) {
        @Builder
        public FeedsRequest(Long cursor, Integer size) {
                if (Objects.isNull(size)) {
                        size = 10;
                }
                this.cursor = cursor;
                this.size = size;
        }
}
