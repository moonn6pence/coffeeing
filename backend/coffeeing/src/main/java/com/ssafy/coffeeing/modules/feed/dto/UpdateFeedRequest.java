package com.ssafy.coffeeing.modules.feed.dto;

import com.ssafy.coffeeing.modules.search.domain.Tag;
import org.hibernate.validator.constraints.Length;

public record UpdateFeedRequest(

        @Length(max = 2200) String content,

        Tag tag
) { }
