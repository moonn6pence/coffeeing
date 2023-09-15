package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeBookmark;
import com.ssafy.coffeeing.modules.product.domain.CoffeeReview;
import com.ssafy.coffeeing.modules.product.dto.CoffeeResponse;
import com.ssafy.coffeeing.modules.product.dto.SimilarProductResponse;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CoffeeBookmarkRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CoffeeService {

    private final SecurityContextUtils securityContextUtils;

    private final CoffeeRepository coffeeRepository;

    private final CoffeeReviewRepository coffeeReviewRepository;

    private final CoffeeBookmarkRepository coffeeBookmarkRepository;

    @Transactional(readOnly = true)
    public CoffeeResponse getDetail(Long id) {

        Coffee coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));

        Boolean isBookmarked = Boolean.FALSE;

        CoffeeReview memberReview = null;

        Member member = securityContextUtils.getMemberIdByTokenOptionalRequest();

        if (member != null) {

            isBookmarked = coffeeBookmarkRepository.existsByCoffeeAndMember(coffee, member);
            memberReview = coffeeReviewRepository.findCoffeeReviewByCoffeeAndMember(coffee, member);
        }

        return ProductMapper.supplyCoffeeResponseOf(coffee, isBookmarked, memberReview);
    }

    public ToggleResponse toggleBookmark(Long id) {

        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        Coffee coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_PRODUCT));

        CoffeeBookmark bookmark = coffeeBookmarkRepository.findByCoffeeAndMember(coffee, member);

        // 찜 등록
        if (bookmark == null) {
            bookmark = CoffeeBookmark
                    .builder()
                    .coffee(coffee)
                    .member(member)
                    .build();

            coffeeBookmarkRepository.save(bookmark);

            return new ToggleResponse(Boolean.TRUE);
        }

        // 찜 해제
        coffeeBookmarkRepository.delete(bookmark);

        return new ToggleResponse(Boolean.FALSE);
    }


    @Transactional(readOnly = true)
    public SimilarProductResponse getSimilarCoffees(Long id) {
        return null;
    }
}
