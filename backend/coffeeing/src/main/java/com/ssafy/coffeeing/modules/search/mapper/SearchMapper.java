package com.ssafy.coffeeing.modules.search.mapper;

import com.ssafy.coffeeing.modules.search.domain.Tag;
import com.ssafy.coffeeing.modules.search.dto.ProductSearchElement;
import com.ssafy.coffeeing.modules.search.dto.SearchProductResponse;
import com.ssafy.coffeeing.modules.search.dto.TagsResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchMapper {

    public static TagsResponse supplyTagsResponseFrom(List<Tag> tags) {
        return new TagsResponse(tags);
    }

    public static SearchProductResponse supplySearchProductResponseOf(List<ProductSearchElement> products, Integer totalCount){
        return new SearchProductResponse(products, totalCount);
    }
}
