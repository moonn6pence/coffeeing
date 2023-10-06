package com.ssafy.coffeeing.modules.product.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public record ReviewRequest(
        @NotNull @Range(min = 1, max = 5) Integer score,
        @Length(min = 3, max = 1000, message = "3자 이상, 1000자 이하의 텍스트만 가능합니다.") String content
) {
}
