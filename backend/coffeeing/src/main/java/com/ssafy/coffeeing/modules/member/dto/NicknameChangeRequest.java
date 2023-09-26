package com.ssafy.coffeeing.modules.member.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

public record NicknameChangeRequest(
        @Pattern.List({
                @Pattern(regexp = "^(?!\\s)(?!.*\\s$)(?!.*\\s{2}).*$", message = "공백문자를 연달아 사용하거나, 처음과 마지막에 사용할 수 없습니다."),
                @Pattern(regexp = "^[\\w\\s가-힣ㄱ-ㅎ]*$", message = "한글, 영문, 숫자, _, 공백만 사용할 수 있습니다.")
        })
        @Length(min = 1, max = 11)
        String nickname
) {
}
