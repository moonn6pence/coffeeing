package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleBookmark;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;
import com.ssafy.coffeeing.modules.product.dto.CapsuleResponse;
import com.ssafy.coffeeing.modules.product.dto.SimilarProductResponse;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CapsuleBookmarkRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CapsuleService {

    private final SecurityContextUtils securityContextUtils;

    private final CapsuleRepository capsuleRepository;

    private final CapsuleReviewRepository capsuleReviewRepository;

    private final CapsuleBookmarkRepository capsuleBookmarkRepository;

    @Transactional(readOnly = true)
    public CapsuleResponse getDetail(Long id) {
        Capsule capsule = capsuleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));

        Boolean isBookmarked = Boolean.FALSE;

        CapsuleReview memberReview = null;

        Member member = securityContextUtils.getMemberIdByTokenOptionalRequest();

        if (member != null) {

            isBookmarked = capsuleBookmarkRepository.existsByCapsuleAndMember(capsule, member);

            memberReview = capsuleReviewRepository.findCapsuleReviewByCapsuleAndMember(capsule, member);
        }

        return ProductMapper.supplyCapsuleResponseFrom(capsule, isBookmarked, memberReview);
    }

    public ToggleResponse toggleBookmark(Long id) {

        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        Capsule capsule = capsuleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));

        CapsuleBookmark bookmark = capsuleBookmarkRepository.findByCapsuleAndMember(capsule, member);

        // 찜 등록
        if (bookmark == null) {
            bookmark = CapsuleBookmark
                    .builder()
                    .capsule(capsule)
                    .member(member)
                    .build();

            capsuleBookmarkRepository.save(bookmark);

            return new ToggleResponse(Boolean.TRUE);
        }

        // 찜 해제
        capsuleBookmarkRepository.delete(bookmark);

        return new ToggleResponse(Boolean.FALSE);
    }

    @Transactional(readOnly = true)
    public SimilarProductResponse getSimilarCapsules(Long id) {
        return null;
    }
}
