package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.modules.product.dto.CapsuleReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CapsuleReviewService {

    @Transactional(readOnly = true)
    public CapsuleReviewResponse getCapsuleReviews(Long id) {
        return null;
    }
}
