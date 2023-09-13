package com.ssafy.coffeeing.modules.feed.dto;

import javax.validation.constraints.Size;
import java.util.List;

public record UploadFeedRequest(
        @Size(min = 1, max = 10)
        List<ImageElement> images,
        String content
) { }
