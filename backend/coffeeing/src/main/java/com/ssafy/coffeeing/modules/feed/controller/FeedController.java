package com.ssafy.coffeeing.modules.feed.controller;

import com.ssafy.coffeeing.modules.feed.dto.*;
import com.ssafy.coffeeing.modules.feed.service.FeedService;
import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
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

    @PostMapping("/{feedId}/like")
    public BaseResponse<ToggleResponse> toggleFeedLike(@PathVariable @NumberFormat Long feedId) {
        return BaseResponse.<ToggleResponse>builder()
                .data(feedService.toggleFeedLike(feedId))
                .build();
    }

    @GetMapping("/my-list")
    public BaseResponse<MyFeedsResponse> getMyFeeds(@Valid MyFeedsRequest myFeedRequest) {
        return BaseResponse.<MyFeedsResponse>builder()
                .build();
    }

    @GetMapping("/{memberId}/list")
    public BaseResponse<MemberFeedsResponse> getFeedsByMemberId(@Valid MemberFeedsRequest memberFeedsRequest) {
        return BaseResponse.<MemberFeedsResponse>builder()
                .build();
    }
}
