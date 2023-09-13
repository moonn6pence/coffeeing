package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.modules.product.dto.CoffeeReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CoffeeReviewService {

    @Transactional(readOnly = true)
    public CoffeeReviewResponse getCoffeeReviews(Long id) {
        return null;
    }
}
