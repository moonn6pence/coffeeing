package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.modules.event.eventer.ExperienceEvent;
import com.ssafy.coffeeing.modules.global.dto.CreationResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.AuthErrorInfo;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeReview;
import com.ssafy.coffeeing.modules.product.dto.PageInfoRequest;
import com.ssafy.coffeeing.modules.product.dto.ProductReviewResponse;
import com.ssafy.coffeeing.modules.product.dto.ReviewRequest;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeReviewQueryRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CoffeeReviewService {

    private final CoffeeRepository coffeeRepository;

    private final CoffeeReviewRepository coffeeReviewRepository;

    private final CoffeeReviewQueryRepository coffeeReviewQueryRepository;

    private final SecurityContextUtils securityContextUtils;

    private final ApplicationEventPublisher applicationEventPublisher;

    private static final Integer REVIEW_PAGE_SIZE = 6;
    private static final Integer REVIEW_EXPERIENCE = 50;
    @Transactional
    public CreationResponse createReview(Long id, ReviewRequest reviewRequest) {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        Coffee coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));

        if (coffeeReviewRepository.existsByCoffeeAndMember(coffee, member)) {
            throw new BusinessException(ProductErrorInfo.DUPLICATE_REVIEW);
        }

        CoffeeReview review = coffeeReviewRepository.save(
                ProductMapper.supplyCoffeeReviewOf(coffee, member, reviewRequest));

        coffee.addReview(reviewRequest.score());

        applicationEventPublisher.publishEvent(new ExperienceEvent(REVIEW_EXPERIENCE,member.getId()));

        return ProductMapper.supplyCreationResponseFrom(review);
    }

    @Transactional(readOnly = true)
    public ProductReviewResponse getReviews(Long id, PageInfoRequest pageInfoRequest) {

        Member member = securityContextUtils.getMemberIdByTokenOptionalRequest();

        Coffee coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));

        Page<CoffeeReview> coffeeReviews = coffeeReviewQueryRepository
                .findOthersCoffeeReviews(coffee, member, pageInfoRequest.getPageableWithSize(REVIEW_PAGE_SIZE));

        return ProductMapper.supplyCoffeeReviewResponseFrom(coffeeReviews);


    }

    @Transactional
    public void updateReview(Long id, ReviewRequest reviewRequest) {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        CoffeeReview review = coffeeReviewRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_REVIEW));

        if (!review.getMember().getId().equals(member.getId())) {
            throw new BusinessException(AuthErrorInfo.UNAUTHORIZED);
        }

        review.getCoffee().updateReview(reviewRequest.score() - review.getCoffee().getTotalScore());

        review.update(reviewRequest.content(), reviewRequest.score());
    }

    @Transactional
    public void deleteReview(Long id) {

        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        CoffeeReview review = coffeeReviewRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_REVIEW));

        review.getCoffee().deleteReview(review.getScore());

        if (!review.getMember().getId().equals(member.getId())) {
            throw new BusinessException(AuthErrorInfo.UNAUTHORIZED);
        }

        coffeeReviewRepository.delete(review);
    }
}
