package com.ssafy.coffeeing.modules.tag.dto;

import com.ssafy.coffeeing.modules.tag.domain.Tag;

import java.util.List;

public record TagsResponse(
        List<Tag> tags
) { }
