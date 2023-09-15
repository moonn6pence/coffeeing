package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeReview;
import com.ssafy.coffeeing.modules.product.dto.PageInfoRequest;
import com.ssafy.coffeeing.modules.product.dto.ProductReviewResponse;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeReviewQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CoffeeReviewService {

    private final CoffeeRepository coffeeRepository;

    private final CoffeeReviewQueryRepository coffeeReviewQueryRepository;

    private final SecurityContextUtils securityContextUtils;

    private static final Integer REVIEW_PAGE_SIZE = 6;

    @Transactional(readOnly = true)
    public ProductReviewResponse getCoffeeReviews(Long id, PageInfoRequest pageInfoRequest) {

        Member member = securityContextUtils.getMemberIdByTokenOptionalRequest();

        Coffee coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));

        Page<CoffeeReview> capsuleReviews = coffeeReviewQueryRepository
                .findOthersCoffeeReviews(coffee, member, pageInfoRequest.getPageableWithSize(REVIEW_PAGE_SIZE));

        return ProductMapper.supplyCoffeeReviewResponseFrom(capsuleReviews);


    }
}
