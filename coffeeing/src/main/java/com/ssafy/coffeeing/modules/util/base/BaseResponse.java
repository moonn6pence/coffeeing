package com.ssafy.coffeeing.modules.util.base;

import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BaseResponse<T> {
    @Builder.Default
    private String code = "200";

    @Builder.Default
    private String message = "success";

    private T data;
}
