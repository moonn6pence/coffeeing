package com.ssafy.coffeeing.modules.tag.domain;

public record Tag(
        Long tagId,
        TagType category,
        String name
) { }
