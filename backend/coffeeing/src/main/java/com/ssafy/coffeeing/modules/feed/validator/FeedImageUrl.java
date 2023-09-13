package com.ssafy.coffeeing.modules.feed.validator;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = FeedImageUrlValidator.class)
public @interface FeedImageUrl {
    String message() default "이미지 URL 형식이 잘못됐습니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}