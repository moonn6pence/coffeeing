package com.ssafy.coffeeing.modules.search.domain;

public record Tag(
        Long tagId,
        TagType category,
        String name
) { }
