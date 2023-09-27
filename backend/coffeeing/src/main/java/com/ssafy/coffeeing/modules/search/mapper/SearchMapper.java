package com.ssafy.coffeeing.modules.search.mapper;

import com.ssafy.coffeeing.modules.search.domain.Tag;
import com.ssafy.coffeeing.modules.search.dto.BeanSearchElement;
import com.ssafy.coffeeing.modules.search.dto.CapsuleSearchElement;
import com.ssafy.coffeeing.modules.search.dto.SearchBeanResponse;
import com.ssafy.coffeeing.modules.search.dto.SearchCapsuleResponse;
import com.ssafy.coffeeing.modules.search.dto.TagsResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchMapper {

    public static TagsResponse supplyTagsResponseFrom(List<Tag> tags) {
        return new TagsResponse(tags);
    }

    public static SearchBeanResponse supplySearchBeanResponseOf(
            List<BeanSearchElement> products, Integer currentPage, Boolean isLast, Integer totalCount) {
        return new SearchBeanResponse(products, currentPage, isLast, totalCount);
    }

    public static SearchCapsuleResponse supplySearchCapsuleResponseOf(
            List<CapsuleSearchElement> products, Integer currentPage, Boolean isLast, Integer totalCount) {
        return new SearchCapsuleResponse(products, currentPage, isLast, totalCount);
    }
}
