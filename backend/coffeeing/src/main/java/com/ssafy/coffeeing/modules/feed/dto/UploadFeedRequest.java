package com.ssafy.coffeeing.modules.feed.dto;

import com.ssafy.coffeeing.modules.tag.dto.TagElement;

import javax.validation.constraints.Size;
import java.util.List;

public record UploadFeedRequest(

        TagElement tagElement,

        @Size(min = 1, max = 10)
        List<ImageElement> images,

        String content
) { }
