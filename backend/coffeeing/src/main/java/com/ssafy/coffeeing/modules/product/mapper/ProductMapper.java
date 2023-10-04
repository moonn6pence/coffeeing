package com.ssafy.coffeeing.modules.product.mapper;

import com.ssafy.coffeeing.modules.global.dto.CreationResponse;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.dto.BookmarkResponse;
import com.ssafy.coffeeing.modules.member.dto.CapsuleBookmarkElement;
import com.ssafy.coffeeing.modules.member.dto.CapsuleBookmarkResponse;
import com.ssafy.coffeeing.modules.member.dto.CoffeeBookmarkElement;
import com.ssafy.coffeeing.modules.member.dto.CoffeeBookmarkResponse;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeReview;
import com.ssafy.coffeeing.modules.product.dto.CapsuleResponse;
import com.ssafy.coffeeing.modules.product.dto.CoffeeResponse;
import com.ssafy.coffeeing.modules.product.dto.ProductReviewElement;
import com.ssafy.coffeeing.modules.product.dto.ProductReviewResponse;
import com.ssafy.coffeeing.modules.product.dto.ReviewRequest;
import com.ssafy.coffeeing.modules.product.dto.SimpleProductElement;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper {

    public static CapsuleResponse supplyCapsuleResponseOf(Capsule capsule,
                                                          Boolean isBookmarked,
                                                          CapsuleReview memberReview) {

        return new CapsuleResponse(
                capsule.getId(), capsule.getBrandKr(), capsule.getCapsuleNameKr(), capsule.getImageUrl(),
                capsule.getFlavorNote(), capsule.getCoffeeCriteria().getRoast(), capsule.getCoffeeCriteria().getAcidity(),
                capsule.getCoffeeCriteria().getBody(), capsule.getProductDescription(),
                capsule.getTotalReviewer() == 0 ? 0.0 : capsule.getTotalScore() / (double) capsule.getTotalReviewer(),
                isBookmarked,
                memberReview != null,
                memberReview != null ? supplyProductReviewElementFrom(memberReview) : null);
    }

    public static CoffeeResponse supplyCoffeeResponseOf(Coffee coffee, Boolean isBookmarked,
                                                        CoffeeReview memberReview) {

        return new CoffeeResponse(
                coffee.getId(), coffee.getRegionKr(), coffee.getCoffeeNameKr(), coffee.getImageUrl(),
                coffee.getFlavorNote(), coffee.getCoffeeCriteria().getRoast(), coffee.getCoffeeCriteria().getAcidity(),
                coffee.getCoffeeCriteria().getBody(), coffee.getProductDescription(),
                coffee.getTotalReviewer() == 0 ? 0.0 : coffee.getTotalScore() / (double) coffee.getTotalReviewer(),
                isBookmarked,
                memberReview != null,
                memberReview != null ? supplyProductReviewElementFrom(memberReview) : null);
    }


    public static ProductReviewElement supplyProductReviewElementFrom(CapsuleReview capsuleReview) {

        return new ProductReviewElement(
                capsuleReview.getId(),
                capsuleReview.getScore(),
                capsuleReview.getContent(),
                capsuleReview.getMember().getId(),
                capsuleReview.getMember().getProfileImage(),
                capsuleReview.getMember().getNickname()
        );
    }

    private static ProductReviewElement supplyProductReviewElementFrom(CoffeeReview coffeeReview) {
        return new ProductReviewElement(
                coffeeReview.getId(),
                coffeeReview.getScore(),
                coffeeReview.getContent(),
                coffeeReview.getMember().getId(),
                coffeeReview.getMember().getProfileImage(),
                coffeeReview.getMember().getNickname()
        );
    }

    public static ProductReviewResponse supplyCapsuleReviewResponseFrom(Page<CapsuleReview> reviews) {

        return new ProductReviewResponse(reviews.getNumber(), reviews.getTotalPages(),
                reviews.getContent().stream().map(ProductMapper::supplyProductReviewElementFrom).toList());
    }

    public static ProductReviewResponse supplyCoffeeReviewResponseFrom(Page<CoffeeReview> reviews) {

        return new ProductReviewResponse(reviews.getNumber(), reviews.getTotalPages(),
                reviews.getContent().stream().map(ProductMapper::supplyProductReviewElementFrom).toList());
    }

    public static CreationResponse supplyCreationResponseFrom(CapsuleReview review) {

        return new CreationResponse(review.getId());
    }

    public static CreationResponse supplyCreationResponseFrom(CoffeeReview review) {

        return new CreationResponse(review.getId());
    }

    public static CapsuleReview supplyCapsuleReviewOf(Capsule capsule, Member member, ReviewRequest reviewRequest) {

        return CapsuleReview.builder()
                .capsule(capsule)
                .member(member)
                .content(reviewRequest.content())
                .score(reviewRequest.score())
                .build();
    }


    public static CoffeeReview supplyCoffeeReviewOf(Coffee coffee, Member member, ReviewRequest reviewRequest) {

        return CoffeeReview.builder()
                .coffee(coffee)
                .member(member)
                .content(reviewRequest.content())
                .score(reviewRequest.score())
                .build();
    }

    public static CoffeeBookmarkElement supplyCoffeeBookmarkElementOf(
            Long id,
            String regionKr,
            String nameKr,
            String imageUrl
    ) {
        return new CoffeeBookmarkElement(id, regionKr, nameKr, imageUrl);
    }

    public static CapsuleBookmarkElement supplyCapsuleBookmarkElementOf(
            Long id,
            String brandKr,
            String nameKr,
            String imageUrl
    ) {
        return new CapsuleBookmarkElement(id, brandKr, nameKr, imageUrl);
    }

    public static SimpleProductElement supplySimpleProductElementFrom(Capsule capsule) {

        return new SimpleProductElement(capsule.getId(), capsule.getBrandKr(), capsule.getCapsuleNameKr(), capsule.getImageUrl());
    }

    public static SimpleProductElement supplySimpleProductElementFrom(Coffee coffee) {

        return new SimpleProductElement(coffee.getId(), coffee.getRegionKr(), coffee.getCoffeeNameKr(), coffee.getImageUrl());
    }

    public static CoffeeBookmarkResponse supplyCoffeeBookmarkResponseOf(Integer number, Integer totalPages, List<CoffeeBookmarkElement> bookmarkedElements, boolean isCapsule) {
        return new BookmarkResponse(
                number,
                totalPages,
                bookmarkedElements,
                isCapsule
        );
    }
    public static CapsuleBookmarkResponse supplyCapsuleBookmarkResponseOf(Integer number, Integer totalPages, List<CapsuleBookmarkElement> bookmarkedElements, boolean isCapsule){
        return new CapsuleBookmarkResponse(
                number,
                totalPages,
                bookmarkedElements,
                isCapsule
        );
    }

}
