package com.ssafy.coffeeing.modules.product.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ScoreValidator implements ConstraintValidator<CheckScore, Double> {

    double epsilon = 1e-10;

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {

        if (value < 0.5 || value > 5) return false;

        if (Math.abs(value % 0.5) >= epsilon) return false;

        return true;
    }
}
