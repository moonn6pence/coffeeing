package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;
import com.ssafy.coffeeing.modules.product.dto.CapsuleReviewResponse;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleReviewQueryRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CapsuleReviewService {

    private final CapsuleRepository capsuleRepository;

    private final CapsuleReviewRepository capsuleReviewRepository;

    private final CapsuleReviewQueryRepository capsuleReviewQueryRepository;

    private final SecurityContextUtils securityContextUtils;

    @Transactional(readOnly = true)
    public CapsuleReviewResponse getCapsuleReviews(Long id) {

        Member member = securityContextUtils.getMemberIdByTokenOptionalRequest();

        Capsule capsule = capsuleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));

        List<CapsuleReview> capsuleReviews = capsuleReviewQueryRepository.findOthersCapsuleReviews(capsule, member);

        if (member != null) {
            CapsuleReview memberReview = capsuleReviewRepository.findCapsuleReviewByCapsuleAndMember(capsule, member);


        }

        return null;
    }
}
