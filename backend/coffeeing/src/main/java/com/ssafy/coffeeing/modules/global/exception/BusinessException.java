package com.ssafy.coffeeing.modules.global.exception;

import com.ssafy.coffeeing.modules.global.exception.info.ErrorInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException{

    private ErrorInfo info;
}
