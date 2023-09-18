package com.ssafy.coffeeing.modules.product.controller;

import com.ssafy.coffeeing.modules.global.dto.CreationResponse;
import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.product.dto.CapsuleResponse;
import com.ssafy.coffeeing.modules.product.dto.CoffeeResponse;
import com.ssafy.coffeeing.modules.product.dto.PageInfoRequest;
import com.ssafy.coffeeing.modules.product.dto.ProductReviewResponse;
import com.ssafy.coffeeing.modules.product.dto.ReviewRequest;
import com.ssafy.coffeeing.modules.product.dto.SimilarProductResponse;
import com.ssafy.coffeeing.modules.product.service.CapsuleReviewService;
import com.ssafy.coffeeing.modules.product.service.CapsuleService;
import com.ssafy.coffeeing.modules.product.service.CoffeeReviewService;
import com.ssafy.coffeeing.modules.product.service.CoffeeService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/product")
@RestController
public class ProductController {

    private final CapsuleService capsuleService;

    private final CoffeeService coffeeService;

    private final CapsuleReviewService capsuleReviewService;

    private final CoffeeReviewService coffeeReviewService;

    @GetMapping("/capsule/{capsuleId}")
    public BaseResponse<CapsuleResponse> getCapsuleDetail(@PathVariable @NumberFormat Long capsuleId) {
        return BaseResponse.<CapsuleResponse>builder()
                .data(capsuleService.getDetail(capsuleId))
                .build();
    }

    @GetMapping("/coffee/{coffeeId}")
    public BaseResponse<CoffeeResponse> getCoffeeDetail(@PathVariable @NumberFormat Long coffeeId) {
        return BaseResponse.<CoffeeResponse>builder()
                .data(coffeeService.getDetail(coffeeId))
                .build();
    }

    @PostMapping("/capsule/{capsuleId}/bookmark")
    public BaseResponse<ToggleResponse> toggleCapsuleBookmark(@PathVariable @NumberFormat Long capsuleId) {
        return BaseResponse.<ToggleResponse>builder()
                .data(capsuleService.toggleBookmark(capsuleId))
                .build();
    }

    @PostMapping("/coffee/{coffeeId}/bookmark")
    public BaseResponse<ToggleResponse> toggleCoffeeBookmark(@PathVariable @NumberFormat Long coffeeId) {
        return BaseResponse.<ToggleResponse>builder()
                .data(coffeeService.toggleBookmark(coffeeId))
                .build();
    }

    @GetMapping("/capsule/{capsuleId}/review")
    public BaseResponse<ProductReviewResponse> getCapsuleReviews(@PathVariable @NumberFormat Long capsuleId,
                                                                 @Valid PageInfoRequest pageInfoRequest) {
        return BaseResponse.<ProductReviewResponse>builder()
                .data(capsuleReviewService.getReviews(capsuleId, pageInfoRequest))
                .build();
    }

    @GetMapping("/coffee/{coffeeId}/review")
    public BaseResponse<ProductReviewResponse> getCoffeeReviews(@PathVariable @NumberFormat Long coffeeId,
                                                                @Valid PageInfoRequest pageInfoRequest) {
        return BaseResponse.<ProductReviewResponse>builder()
                .data(coffeeReviewService.getReviews(coffeeId, pageInfoRequest))
                .build();
    }

    @GetMapping("/capsule/{capsuleId}/similar")
    public BaseResponse<SimilarProductResponse> getSimilarCapsules(@PathVariable @NumberFormat Long capsuleId) {
        return BaseResponse.<SimilarProductResponse>builder()
                .data(capsuleService.getSimilarCapsules(capsuleId))
                .build();
    }

    @GetMapping("/coffee/{coffeeId}/similar")
    public BaseResponse<SimilarProductResponse> getSimilarCoffees(@PathVariable @NumberFormat Long coffeeId) {
        return BaseResponse.<SimilarProductResponse>builder()
                .data(coffeeService.getSimilarCoffees(coffeeId))
                .build();
    }

    @PostMapping("/capsule/{capsuleId}/review")
    public BaseResponse<CreationResponse> createCapsuleReview(@PathVariable @NumberFormat Long capsuleId,
                                                              @Valid @RequestBody ReviewRequest reviewRequest) {

        return BaseResponse.<CreationResponse>builder()
                .data(capsuleReviewService.createReview(capsuleId, reviewRequest))
                .build();
    }

    @PostMapping("/coffee/{coffeeId}/review")
    public BaseResponse<CreationResponse> createCoffeeReview(@PathVariable @NumberFormat Long id,
                                                             @Valid @RequestBody ReviewRequest reviewRequest) {

        return BaseResponse.<CreationResponse>builder()
                .data(coffeeReviewService.createReview(id, reviewRequest))
                .build();
    }

    @PutMapping("/capsule/review/{reviewId}")
    public BaseResponse<Void> editCapsuleReview(@PathVariable @NumberFormat Long reviewId,
                                                @Valid @RequestBody ReviewRequest reviewRequest) {

        capsuleReviewService.updateReview(reviewId, reviewRequest);
        return BaseResponse.<Void>builder().build();
    }

    @PutMapping("/coffee/review/{reviewId}")
    public BaseResponse<Void> editCoffeeReview(@PathVariable @NumberFormat Long reviewId,
                                               @Valid @RequestBody ReviewRequest reviewRequest) {

        coffeeReviewService.updateReview(reviewId, reviewRequest);
        return BaseResponse.<Void>builder().build();
    }

    @DeleteMapping("/capsule/review/{reviewId}")
    public BaseResponse<Void> deleteCapsuleReview(@PathVariable @NumberFormat Long reviewId) {

        capsuleReviewService.deleteReview(reviewId);
        return BaseResponse.<Void>builder().build();
    }

    @DeleteMapping("/coffee/review/{reviewId}")
    public BaseResponse<Void> deleteCoffeeReview(@PathVariable @NumberFormat Long reviewId) {

        coffeeReviewService.deleteReview(reviewId);
        return BaseResponse.<Void>builder().build();
    }
}
