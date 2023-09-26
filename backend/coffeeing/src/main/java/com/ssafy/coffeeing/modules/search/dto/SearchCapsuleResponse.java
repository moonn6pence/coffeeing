package com.ssafy.coffeeing.modules.search.dto;

import java.util.List;

public record SearchCapsuleResponse(

        List<CapsuleSearchElement> products,
        Integer currentPage,
        Boolean isLast,
        Integer totalPage
) {
}
