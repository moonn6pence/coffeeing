package com.ssafy.coffeeing.modules.feed.dto;

import com.ssafy.coffeeing.modules.search.domain.Tag;

import javax.validation.constraints.Size;
import java.util.List;

public record UploadFeedRequest(

        Tag tag,

        @Size(min = 1, max = 10)
        List<ImageElement> images,

        String content
) { }
