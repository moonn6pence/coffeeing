package com.ssafy.coffeeing.modules.product.controller;

import com.ssafy.coffeeing.modules.global.dto.CreationResponse;
import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.product.dto.*;
import com.ssafy.coffeeing.modules.product.service.CapsuleReviewService;
import com.ssafy.coffeeing.modules.product.service.CapsuleService;
import com.ssafy.coffeeing.modules.product.service.CoffeeReviewService;
import com.ssafy.coffeeing.modules.product.service.CoffeeService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/product")
@RestController
public class ProductController {

    private final CapsuleService capsuleService;

    private final CoffeeService coffeeService;

    private final CapsuleReviewService capsuleReviewService;

    private final CoffeeReviewService coffeeReviewService;

    @GetMapping("/capsule/{id}")
    public BaseResponse<CapsuleResponse> getCapsuleDetail(@PathVariable @NumberFormat Long id){
        return BaseResponse.<CapsuleResponse>builder()
                .data(capsuleService.getDetail(id))
                .build();
    }

    @GetMapping("/coffee/{id}")
    public BaseResponse<CoffeeResponse> getCoffeeDetail(@PathVariable @NumberFormat Long id){
        return BaseResponse.<CoffeeResponse>builder()
                .data(coffeeService.getDetail(id))
                .build();
    }

    @PostMapping("/capsule/bookmark/{id}")
    public BaseResponse<ToggleResponse> toggleCapsuleBookmark(@PathVariable @NumberFormat Long id) {
        return BaseResponse.<ToggleResponse>builder()
                .data(capsuleService.toggleBookmark(id))
                .build();
    }

    @PostMapping("/coffee/bookmark/{id}")
    public BaseResponse<ToggleResponse> toggleCoffeeBookmark(@PathVariable @NumberFormat Long id) {
        return BaseResponse.<ToggleResponse>builder()
                .data(coffeeService.toggleBookmark(id))
                .build();
    }

    @GetMapping("/capsule/review/{id}")
    public BaseResponse<CapsuleReviewResponse> getCapsuleReviews(@PathVariable @NumberFormat Long id){
        return BaseResponse.<CapsuleReviewResponse>builder()
                .data(capsuleReviewService.getCapsuleReviews(id))
                .build();
    }

    @GetMapping("/coffee/review/{id}")
    public BaseResponse<CoffeeReviewResponse> getCoffeeReviews(@PathVariable @NumberFormat Long id){
        return BaseResponse.<CoffeeReviewResponse>builder()
                .data(coffeeReviewService.getCoffeeReviews(id))
                .build();
    }

    @GetMapping("/capsule/similar/{id}")
    public BaseResponse<SimilarProductResponse> getSimilarCapsules(@PathVariable @NumberFormat Long id){
        return BaseResponse.<SimilarProductResponse>builder()
                .data(capsuleService.getSimilarCapsules(id))
                .build();
    }

    @GetMapping("/coffee/similar/{id}")
    public BaseResponse<SimilarProductResponse> getSimilarCoffees(@PathVariable @NumberFormat Long id){
        return BaseResponse.<SimilarProductResponse>builder()
                .data(coffeeService.getSimilarCapsules(id))
                .build();
    }

    @PostMapping("/capsule/review/{id}")
    public BaseResponse<CreationResponse> createCapsuleReview(@RequestBody ReviewRequest reviewRequest){

        return BaseResponse.<CreationResponse>builder().build();
    }

    @PostMapping("/coffee/review/{id}")
    public BaseResponse<CreationResponse> createCoffeeReview(@RequestBody ReviewRequest reviewRequest){

        return BaseResponse.<CreationResponse>builder().build();
    }

    @PutMapping("/capsule/review/{id}")
    public BaseResponse<Void> editCapsuleReview(@RequestBody ReviewRequest reviewRequest){

        return BaseResponse.<Void>builder().build();
    }

    @PutMapping("/coffee/review/{id}")
    public BaseResponse<Void> editCoffeeReview(@RequestBody ReviewRequest reviewRequest){

        return BaseResponse.<Void>builder().build();
    }

    @DeleteMapping("/capsule/review/{id}")
    public BaseResponse<Void> deleteCapsuleReview(@PathVariable @NumberFormat Long id){

        return BaseResponse.<Void>builder().build();
    }

    @DeleteMapping("/coffee/review/{id}")
    public BaseResponse<Void> deleteCoffeeReview(@PathVariable @NumberFormat Long id){

        return BaseResponse.<Void>builder().build();
    }

}
