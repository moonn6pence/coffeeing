package com.ssafy.coffeeing.modules.tag.dto;

import java.util.List;

public record TagsResponse(
        List<TagElement> tags
) { }
