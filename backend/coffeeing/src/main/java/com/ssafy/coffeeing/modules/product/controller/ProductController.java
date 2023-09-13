package com.ssafy.coffeeing.modules.product.controller;

import com.ssafy.coffeeing.modules.product.dto.CapsuleResponse;
import com.ssafy.coffeeing.modules.product.service.CapsuleService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/product")
@RestController
public class ProductController {

    private final CapsuleService capsuleService;

    @GetMapping("/capsule/{id}")
    public BaseResponse<CapsuleResponse> getCapsuleDetail(@PathVariable Long id){
        return BaseResponse.<CapsuleResponse>builder()
                .data(capsuleService.getDetail(id))
                .build();
    }
}
