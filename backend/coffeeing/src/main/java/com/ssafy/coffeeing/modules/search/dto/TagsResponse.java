package com.ssafy.coffeeing.modules.search.dto;

import com.ssafy.coffeeing.modules.search.domain.Tag;

import java.util.List;

public record TagsResponse(
        List<Tag> tags
) { }
