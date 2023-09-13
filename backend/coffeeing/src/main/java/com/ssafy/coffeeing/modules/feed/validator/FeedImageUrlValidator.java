package com.ssafy.coffeeing.modules.feed.validator;

import com.ssafy.coffeeing.modules.feed.dto.ImageElement;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class FeedImageUrlValidator implements ConstraintValidator<FeedImageUrl, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.contains(".webp");
    }
}
