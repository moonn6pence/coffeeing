package com.ssafy.coffeeing.modules.product.mapper;

import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.dto.CapsuleResponse;
import com.ssafy.coffeeing.modules.product.dto.CapsuleReviewElement;
import com.ssafy.coffeeing.modules.product.dto.CapsuleReviewResponse;
import com.ssafy.coffeeing.modules.product.dto.CoffeeResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public static CapsuleResponse supplyCapsuleResponseFrom(Capsule capsule, Boolean isBookmarked) {

        return new CapsuleResponse(
                capsule.getId(), capsule.getBrandKr(), capsule.getCapsuleName(), capsule.getImageUrl(),
                capsule.getAroma(), capsule.getCoffeeCriteria().getRoast(), capsule.getCoffeeCriteria().getAcidity(),
                capsule.getCoffeeCriteria().getBody(), capsule.getDescription(),
                capsule.getTotalReviewer() == 0 ? 0.0 : capsule.getTotalScore() / capsule.getTotalReviewer(),
                isBookmarked);
    }

    public static CoffeeResponse supplyCoffeeResponseFrom(Coffee coffee, Boolean isBookmarked) {

        return new CoffeeResponse(
                coffee.getId(), coffee.getCoffeeName(), coffee.getImageUrl(),
                coffee.getAroma(), coffee.getCoffeeCriteria().getRoast(), coffee.getCoffeeCriteria().getAcidity(),
                coffee.getCoffeeCriteria().getBody(), coffee.getDescription(),
                coffee.getTotalReviewer() == 0 ? 0.0 : coffee.getTotalScore() / coffee.getTotalReviewer(),
                isBookmarked);
    }

    public static CapsuleReviewElement supplyCapsuleReviewElementFrom(CapsuleReview capsuleReview) {

        return new CapsuleReviewElement(
                capsuleReview.getId(),
                capsuleReview.getScore(),
                capsuleReview.getContent(),
                capsuleReview.getMember().getNickname()
        );
    }

    public static CapsuleReviewResponse supplyCapsuleReviewResponseOf(Boolean isReviewed,
                                                                        CapsuleReviewElement memberReview,
                                                                        List<CapsuleReviewElement> reviews) {

        return new CapsuleReviewResponse(isReviewed, memberReview, reviews);
    }
}
