package com.ssafy.coffeeing.modules.curation.controller;

import com.ssafy.coffeeing.modules.curation.dto.CurationResponse;
import com.ssafy.coffeeing.modules.curation.service.CurationService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
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
    public BaseResponse<CurationResponse> getOpenCuration() {
        return BaseResponse.<CurationResponse>builder()
                .data(curationService.getOpenCuration())
                .build();
    }

    @GetMapping("/custom")
    public BaseResponse<CurationResponse> getCustomCuration() {
        return BaseResponse.<CurationResponse>builder()
                .data(curationService.getCustomCuration())
                .build();
    }
}
