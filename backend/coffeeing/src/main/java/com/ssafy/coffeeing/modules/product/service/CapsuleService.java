package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.dto.CapsuleResponse;
import com.ssafy.coffeeing.modules.product.dto.CapsuleReviewResponse;
import com.ssafy.coffeeing.modules.product.dto.SimilarCapsuleResponse;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CapsuleBookmarkRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CapsuleService {

    private final CapsuleRepository capsuleRepository;

    private final CapsuleBookmarkRepository capsuleBookmarkRepository;

    public CapsuleResponse getDetail(Long id) {
        Capsule capsule = capsuleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));

//        Member member = null;
//        Boolean isBookmarked = capsuleBookmarkRepository.existsByCapsuleAndMember(capsule, member);
//
//        return ProductMapper.supplyCapsuleResponseBy(capsule, isBookmarked);

        return ProductMapper.supplyCapsuleResponseBy(capsule, false);
    }

    public Boolean toggleBookmark(Long id) {
        return null;
    }

    public CapsuleReviewResponse getCapsuleReviews(Long id) {
        return null;
    }

    public SimilarCapsuleResponse getSimilarCapsules(Long id) {
        return null;
    }
}
