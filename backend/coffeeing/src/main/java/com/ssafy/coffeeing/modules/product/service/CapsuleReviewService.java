package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;
import com.ssafy.coffeeing.modules.product.dto.ProductReviewResponse;
import com.ssafy.coffeeing.modules.product.dto.PageInfoRequest;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleReviewQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CapsuleReviewService {

    private final CapsuleRepository capsuleRepository;

    private final CapsuleReviewQueryRepository capsuleReviewQueryRepository;

    private final SecurityContextUtils securityContextUtils;

    private static final Integer REVIEW_PAGE_SIZE = 6;

    @Transactional(readOnly = true)
    public ProductReviewResponse getCapsuleReviews(Long id, PageInfoRequest pageInfoRequest) {

        Member member = securityContextUtils.getMemberIdByTokenOptionalRequest();

        Capsule capsule = capsuleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));

        System.out.println(member == null);
        System.out.println(capsule == null);

        Page<CapsuleReview> capsuleReviews = capsuleReviewQueryRepository
                .findOthersCapsuleReviews(capsule, member, pageInfoRequest.getPageableWithSize(REVIEW_PAGE_SIZE));

        return ProductMapper.supplyCapsuleReviewResponseFrom(capsuleReviews);
    }
}
