package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.modules.event.eventer.ExperienceEvent;
import com.ssafy.coffeeing.modules.global.dto.CreationResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.AuthErrorInfo;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;
import com.ssafy.coffeeing.modules.product.dto.PageInfoRequest;
import com.ssafy.coffeeing.modules.product.dto.ProductReviewResponse;
import com.ssafy.coffeeing.modules.product.dto.ReviewRequest;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleReviewQueryRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CapsuleReviewService {

    private final CapsuleRepository capsuleRepository;

    private final CapsuleReviewRepository capsuleReviewRepository;

    private final CapsuleReviewQueryRepository capsuleReviewQueryRepository;

    private final SecurityContextUtils securityContextUtils;

    private final ApplicationEventPublisher applicationEventPublisher;

    private static final Integer REVIEW_PAGE_SIZE = 6;
    private static final Integer REVIEW_EXPERIENCE = 50;
    @Transactional
    public CreationResponse createReview(Long id, ReviewRequest reviewRequest) {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        Capsule capsule = capsuleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));

        if (capsuleReviewRepository.existsByCapsuleAndMember(capsule, member)) {
            throw new BusinessException(ProductErrorInfo.DUPLICATE_REVIEW);
        }

        CapsuleReview review = capsuleReviewRepository.save(
                ProductMapper.supplyCapsuleReviewOf(capsule, member, reviewRequest));

        capsule.addReview(reviewRequest.score());

        applicationEventPublisher.publishEvent(new ExperienceEvent(REVIEW_EXPERIENCE,member.getId()));

        return ProductMapper.supplyCreationResponseFrom(review);
    }

    @Transactional(readOnly = true)
    public ProductReviewResponse getReviews(Long id, PageInfoRequest pageInfoRequest) {

        Member member = securityContextUtils.getMemberIdByTokenOptionalRequest();

        Capsule capsule = capsuleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));

        Page<CapsuleReview> capsuleReviews = capsuleReviewQueryRepository
                .findOthersCapsuleReviews(capsule, member, pageInfoRequest.getPageableWithSize(REVIEW_PAGE_SIZE));

        return ProductMapper.supplyCapsuleReviewResponseFrom(capsuleReviews);
    }

    @Transactional
    public void updateReview(Long id, ReviewRequest reviewRequest) {

        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        CapsuleReview review = capsuleReviewRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_REVIEW));

        if (!review.getMember().getId().equals(member.getId())) {
            throw new BusinessException(AuthErrorInfo.UNAUTHORIZED);
        }

        review.update(reviewRequest.content(), (double) reviewRequest.score());
    }

    public void deleteReview(Long id) {

        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        CapsuleReview review = capsuleReviewRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_REVIEW));

        review.getCapsule().deleteReview(review.getScore());

        if (!review.getMember().getId().equals(member.getId())) {
            throw new BusinessException(AuthErrorInfo.UNAUTHORIZED);
        }

        capsuleReviewRepository.delete(review);
    }
}
