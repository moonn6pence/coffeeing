package com.ssafy.coffeeing.modules.search.dto;

import java.util.List;

public record SearchBeanResponse(

        List<BeanSearchElement> products,
        Integer currentPage,
        Boolean isLast,
        Integer totalPage
) {
}
