package com.ssafy.coffeeing.modules.global.exception.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FeedErrorInfo implements ErrorInfo {
    AWS_S3_CLIENT_EXCEPTION("800", "서버에서 예상치 못한 문제가 발생했습니다."),
    FEED_IMAGES_TO_JSON_STRING_ERROR("801", "서버에서 예상치 못한 문제가 발생했습니다.");

    private final String code;
    private final String message;
}
