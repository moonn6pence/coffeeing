package com.ssafy.coffeeing.modules.global.exception.advice;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.GlobalErrorInfo;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse RuntimeExceptionHandler(RuntimeException e){
        extractLog(e);
        return BaseResponse.builder()
                .code(GlobalErrorInfo.INTERNAL_SERVER_ERROR.getCode())
                .message(GlobalErrorInfo.INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse BusinessExceptionHandler(BusinessException e){
        log.error(e.getInfo().getMessage());
        return BaseResponse.builder()
                .code(e.getInfo().getCode())
                .message(e.getInfo().getMessage())
                .build();
    }

    private void extractLog(RuntimeException e) {
        String lineNumber = String.valueOf(e.getStackTrace()[0].getLineNumber());
        String className = e.getStackTrace()[0].getClassName();
        String methodName = e.getStackTrace()[0].getMethodName();
        String message = e.getMessage();
        log.error("\n" + """
                className : {}, methodName: {}, 예외 발생시킨 line: {}, 이유: {}
                """, className, methodName, lineNumber, message);
    }
}
