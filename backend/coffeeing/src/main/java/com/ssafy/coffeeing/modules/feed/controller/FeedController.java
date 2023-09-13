package com.ssafy.coffeeing.modules.feed.controller;

import com.ssafy.coffeeing.modules.feed.dto.UpdateFeedRequest;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedRequest;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedResponse;
import com.ssafy.coffeeing.modules.feed.service.FeedService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{feedId}")
    public BaseResponse<Void> updateFeedContent(
            @PathVariable @NumberFormat Long feedId,
            @Valid @RequestBody UpdateFeedRequest updateFeedRequest) {
        feedService.updateFeedContentById(feedId, updateFeedRequest);
        return BaseResponse.<Void>builder()
                .build();
    }

    @DeleteMapping("/{feedId}")
    public BaseResponse<Void> deleteFeed(@PathVariable @NumberFormat Long feedId) {
        feedService.deleteFeedById(feedId);
        return BaseResponse.<Void>builder()
                .build();
    }
}
