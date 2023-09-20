package com.ssafy.coffeeing.modules.global.exception.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SurveyErrorInfo implements ErrorInfo {
    EXTERNAL_SERVER_ERROR("1200","모델 서버 오류가 발생했습니다."),
    BAD_API_SERVER_REQUEST("1201", "잘못된 요청입니다.");

    private String code;
    private String message;
}
