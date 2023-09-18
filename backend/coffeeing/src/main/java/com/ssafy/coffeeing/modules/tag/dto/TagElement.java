package com.ssafy.coffeeing.modules.tag.dto;

import com.ssafy.coffeeing.modules.tag.domain.TagType;

public record TagElement(
        Long tagId,
        TagType category,
        String name
) { }
