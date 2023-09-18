package com.ssafy.coffeeing.modules.product.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {ScoreValidator.class})
public @interface CheckScore {

    String message() default "0.5이상 5이하의 0.5 단위의 실수여야합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
