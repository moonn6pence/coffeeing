package com.ssafy.coffeeing.modules.feed.controller;

import com.ssafy.coffeeing.modules.feed.dto.UploadFeedRequest;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedResponse;
import com.ssafy.coffeeing.modules.feed.service.FeedService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds")
public class FeedController {

    private final FeedService feedService;

    @PostMapping
    public BaseResponse<UploadFeedResponse> uploadFeedRequest(@Valid @RequestBody UploadFeedRequest uploadFeedRequest) {
        return BaseResponse.<UploadFeedResponse>builder()
                .data(feedService.uploadFeedByMember(uploadFeedRequest))
                .build();
    }
}
