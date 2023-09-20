package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.tag.dto.SearchTagRequest;

public class TagTestDummy {

    public static SearchTagRequest createSearchTagRequest(String keyword) {
        return new SearchTagRequest(keyword);
    }
}
