package com.ssafy.coffeeing.modules.global.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@Aspect
public class ExecutionLogAspect {

    private final ObjectMapper objectMapper;

    public ExecutionLogAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Pointcut("execution(* com.ssafy.coffeeing.modules..*Controller.*(..))")
    public void ControllerLog() {
    }

    @Around("ControllerLog()")
    public Object ExecutionLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
                RequestContextHolder.getRequestAttributes())).getRequest();

        String controllerName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        Map<String, Object> params = new LinkedHashMap<>();
        try {
            params.put("controller", controllerName);
            params.put("method",  methodName);
            params.put("params",  getParams(request));
            params.put("log_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                    Calendar.getInstance().getTime()));
            params.put("request_uri", request.getRequestURI());
            params.put("http_method", request.getMethod());
        } catch (Exception e) {
            log.error("LoggerAspect - Controller error", e);
        }
        log.info("""
                ControllerLogAspect : {}
                """, params);
        return result;
    }

    @Pointcut("execution(* com.ssafy.coffeeing.modules..*Service.*(..))")
    public void ServiceLog() {
    }

    @AfterThrowing(pointcut = "execution(* com.ssafy.coffeeing.*.*(..))", throwing = "ex")
    public void logException(Exception ex) {
        log.info("\n" + "예외 메시지:" + ex.getMessage() + "\n");
    }

    @Around("ServiceLog()")
    public Object methodLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();

        String serviceName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = proceedingJoinPoint.getSignature().getName();

        Map<String, Object> params = new LinkedHashMap<>();

        try {
            params.put("service", serviceName);
            params.put("method", methodName);
            params.put("params", Arrays.toString(proceedingJoinPoint.getArgs()));
            params.put("log_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(Calendar.getInstance().getTime()));
        } catch (Exception e) {
            log.error("LoggingAspect - Service error", e);
        }
        log.info("""
                ServiceLogAspect : {}
                """, params);
        return result;
    }

    private String getParams(HttpServletRequest request) throws JsonProcessingException {
        Enumeration<String> params = request.getParameterNames();
        Map<String, String> object = new HashMap<>();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String replaceParam = param.replace("\\.", "-");
            object.put(replaceParam, request.getParameter(param));
        }

        return objectMapper.writeValueAsString(object);
    }
}
