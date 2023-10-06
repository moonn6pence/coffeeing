package com.ssafy.coffeeing.modules.aws.controller;

import com.ssafy.coffeeing.modules.feed.dto.PresignedUrlResponse;
import com.ssafy.coffeeing.modules.feed.service.AWSS3Service;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aws")
public class AwsS3Controller {
    private final AWSS3Service awsS3Service;

    @GetMapping("/img")
    @ApiOperation(value = "presignedURL 요청")
    public BaseResponse<PresignedUrlResponse> getImageUrl(){
        return BaseResponse.<PresignedUrlResponse>builder()
                .data(awsS3Service.getPresignedUrlWithImagePath())
                .build();
    }

}
