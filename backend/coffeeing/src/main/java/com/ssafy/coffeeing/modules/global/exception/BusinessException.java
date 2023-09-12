package com.ssafy.coffeeing.modules.global.exception;

import com.ssafy.coffeeing.modules.global.exception.info.ErrorInfo;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private ErrorInfo info;
}
