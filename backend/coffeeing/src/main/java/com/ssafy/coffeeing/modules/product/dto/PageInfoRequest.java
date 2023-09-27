package com.ssafy.coffeeing.modules.product.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record PageInfoRequest(
        @NotNull @Min(value = 0) int page
) {

    public Pageable getPageableWithSize(int size){
        return PageRequest.of(this.page, size);
    }
}
