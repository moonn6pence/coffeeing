package com.ssafy.coffeeing.modules.curation.controller;

import com.ssafy.coffeeing.modules.curation.dto.CurationResponse;
import com.ssafy.coffeeing.modules.curation.service.CurationService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curation")
@RequiredArgsConstructor
public class CurationController {

    private final CurationService curationService;

    @GetMapping("/open")
    @ApiOperation(value = "메인 페이지 인증 여부 상관없이 추천")
    public BaseResponse<CurationResponse> getOpenCuration() {
        return BaseResponse.<CurationResponse>builder()
                .data(curationService.getOpenCuration())
                .build();
    }

    @GetMapping("/custom")
    @ApiOperation(value = "사용자마다 커스텀 큐레이션 추천")
    public BaseResponse<CurationResponse> getCustomCuration() {
        return BaseResponse.<CurationResponse>builder()
                .data(curationService.getCustomCuration())
                .build();
    }
}
