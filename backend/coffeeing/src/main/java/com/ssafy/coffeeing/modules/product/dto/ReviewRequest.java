package com.ssafy.coffeeing.modules.product.dto;

import com.ssafy.coffeeing.modules.product.validator.CheckScore;
import org.hibernate.validator.constraints.Length;

public record ReviewRequest(
        @CheckScore Double score,
        @Length(min = 3, max = 1000, message = "3자 이상, 1000자 이하의 텍스트만 가능합니다.") String content
) {
}
