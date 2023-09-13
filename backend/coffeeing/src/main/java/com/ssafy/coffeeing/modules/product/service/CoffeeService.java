package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.modules.product.dto.CoffeeResponse;
import com.ssafy.coffeeing.modules.product.dto.CoffeeReviewResponse;
import com.ssafy.coffeeing.modules.product.dto.SimilarProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CoffeeService {

    @Transactional(readOnly = true)
    public CoffeeResponse getDetail(Long id) {
        return null;
    }

    public Boolean toggleBookmark(Long id) {
        return null;
    }

    @Transactional(readOnly = true)
    public CoffeeReviewResponse getCoffeeReviews(Long id) {
        return null;
    }

    @Transactional(readOnly = true)
    public SimilarProductResponse getSimilarCapsules(Long id) {
        return null;
    }
}
