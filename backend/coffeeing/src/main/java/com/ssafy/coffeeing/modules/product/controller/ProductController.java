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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "캡슐 상세 정보 조회 요청")
    public BaseResponse<CapsuleResponse> getCapsuleDetail(@PathVariable @NumberFormat Long capsuleId) {
        return BaseResponse.<CapsuleResponse>builder()
                .data(capsuleService.getDetail(capsuleId))
                .build();
    }

    @GetMapping("/coffee/{coffeeId}")
    @ApiOperation(value = "원두 상세 정보 조회 요청")
    public BaseResponse<CoffeeResponse> getCoffeeDetail(@PathVariable @NumberFormat Long coffeeId) {
        return BaseResponse.<CoffeeResponse>builder()
                .data(coffeeService.getDetail(coffeeId))
                .build();
    }

    @PostMapping("/capsule/{capsuleId}/bookmark")
    @ApiOperation(value = "캡슐 북마크 토글 요청")
    public BaseResponse<ToggleResponse> toggleCapsuleBookmark(@PathVariable @NumberFormat Long capsuleId) {
        return BaseResponse.<ToggleResponse>builder()
                .data(capsuleService.toggleBookmark(capsuleId))
                .build();
    }

    @PostMapping("/coffee/{coffeeId}/bookmark")
    @ApiOperation(value = "원두 북마크 토글 요청")
    public BaseResponse<ToggleResponse> toggleCoffeeBookmark(@PathVariable @NumberFormat Long coffeeId) {
        return BaseResponse.<ToggleResponse>builder()
                .data(coffeeService.toggleBookmark(coffeeId))
                .build();
    }

    @ApiImplicitParam(name = "page", dataType = "integer", value = "페이지 번호", paramType = "query")
    @GetMapping("/capsule/{capsuleId}/review")
    @ApiOperation(value = "캡슐 리뷰 리스트 페이지네이션 요청")
    public BaseResponse<ProductReviewResponse> getCapsuleReviews(@PathVariable @NumberFormat Long capsuleId,
                                                                 @Valid PageInfoRequest pageInfoRequest) {
        return BaseResponse.<ProductReviewResponse>builder()
                .data(capsuleReviewService.getReviews(capsuleId, pageInfoRequest))
                .build();
    }

    @ApiImplicitParam(name = "page", dataType = "integer", value = "페이지 번호", paramType = "query")
    @GetMapping("/coffee/{coffeeId}/review")
    @ApiOperation(value = "원두 리뷰 리스트 페이지네이션 요청")
    public BaseResponse<ProductReviewResponse> getCoffeeReviews(@PathVariable @NumberFormat Long coffeeId,
                                                                @Valid PageInfoRequest pageInfoRequest) {
        return BaseResponse.<ProductReviewResponse>builder()
                .data(coffeeReviewService.getReviews(coffeeId, pageInfoRequest))
                .build();
    }

    @GetMapping("/capsule/{capsuleId}/similar")
    @ApiOperation(value = "캡슐 ID와 유사 제품 리스트 요청")
    public BaseResponse<SimilarProductResponse> getSimilarCapsules(@PathVariable @NumberFormat Long capsuleId) {
        return BaseResponse.<SimilarProductResponse>builder()
                .data(capsuleService.getSimilarCapsules(capsuleId))
                .build();
    }

    @GetMapping("/coffee/{coffeeId}/similar")
    @ApiOperation(value = "원두 ID와 유사 제품 리스트 요청")
    public BaseResponse<SimilarProductResponse> getSimilarCoffees(@PathVariable @NumberFormat Long coffeeId) {
        return BaseResponse.<SimilarProductResponse>builder()
                .data(coffeeService.getSimilarCoffees(coffeeId))
                .build();
    }

    @PostMapping("/capsule/{capsuleId}/review")
    @ApiOperation(value = "캡슐 리뷰 작성 요청")
    public BaseResponse<CreationResponse> createCapsuleReview(@PathVariable @NumberFormat Long capsuleId,
                                                              @Valid @RequestBody ReviewRequest reviewRequest) {

        return BaseResponse.<CreationResponse>builder()
                .data(capsuleReviewService.createReview(capsuleId, reviewRequest))
                .build();
    }

    @PostMapping("/coffee/{coffeeId}/review")
    @ApiOperation(value = "원두 리뷰 작성 요청")
    public BaseResponse<CreationResponse> createCoffeeReview(@PathVariable @NumberFormat Long coffeeId,
                                                             @Valid @RequestBody ReviewRequest reviewRequest) {

        return BaseResponse.<CreationResponse>builder()
                .data(coffeeReviewService.createReview(coffeeId, reviewRequest))
                .build();
    }

    @PutMapping("/capsule/review/{reviewId}")
    @ApiOperation(value = "캡슐 리뷰 수정 요청")
    public BaseResponse<Void> editCapsuleReview(@PathVariable @NumberFormat Long reviewId,
                                                @Valid @RequestBody ReviewRequest reviewRequest) {

        capsuleReviewService.updateReview(reviewId, reviewRequest);
        return BaseResponse.<Void>builder().build();
    }

    @PutMapping("/coffee/review/{reviewId}")
    @ApiOperation(value = "원두 리뷰 수정 요청")
    public BaseResponse<Void> editCoffeeReview(@PathVariable @NumberFormat Long reviewId,
                                               @Valid @RequestBody ReviewRequest reviewRequest) {

        coffeeReviewService.updateReview(reviewId, reviewRequest);
        return BaseResponse.<Void>builder().build();
    }

    @DeleteMapping("/capsule/review/{reviewId}")
    @ApiOperation(value = "캡슐 리뷰 삭제")
    public BaseResponse<Void> deleteCapsuleReview(@PathVariable @NumberFormat Long reviewId) {

        capsuleReviewService.deleteReview(reviewId);
        return BaseResponse.<Void>builder().build();
    }

    @DeleteMapping("/coffee/review/{reviewId}")
    @ApiOperation(value = "원두 리뷰 삭제")
    public BaseResponse<Void> deleteCoffeeReview(@PathVariable @NumberFormat Long reviewId) {

        coffeeReviewService.deleteReview(reviewId);
        return BaseResponse.<Void>builder().build();
    }
}
