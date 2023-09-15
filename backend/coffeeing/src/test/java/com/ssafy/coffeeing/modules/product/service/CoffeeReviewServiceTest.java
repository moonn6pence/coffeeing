package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.dummy.CapsuleTestDummy;
import com.ssafy.coffeeing.dummy.CoffeeReviewTestDummy;
import com.ssafy.coffeeing.dummy.CoffeeTestDummy;
import com.ssafy.coffeeing.dummy.MemberTestDummy;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeReview;
import com.ssafy.coffeeing.modules.product.dto.PageInfoRequest;
import com.ssafy.coffeeing.modules.product.dto.ProductReviewResponse;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeReviewRepository;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class CoffeeReviewServiceTest extends ServiceTest {

    @Autowired
    private CoffeeReviewService coffeeReviewService;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeReviewRepository coffeeReviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private SecurityContextUtils securityContextUtils;

    private Coffee coffee;
    private List<Member> members;

    @BeforeEach
    void setUpCapsuleReviews(){
        coffee = coffeeRepository.save(CoffeeTestDummy.createMockCoffeeKenyaAA());
        members = memberRepository.saveAll(MemberTestDummy.create25GeneralMembers());
    }

    @Test
    @DisplayName("미인증 사용자가 커피 아이디와 페이지 번호를 통해 캡슐 리뷰를 조회한다.")
    void Given_CoffeeIdAndPageInfoRequest_When_GetCoffeeReviews_Then_Success() {

        // given
        List<CoffeeReview> capsuleReviews =
                coffeeReviewRepository.saveAll(CoffeeReviewTestDummy.createMockCoffeeReviews(coffee, members));

        Integer page = 0;
        PageInfoRequest pageInfoRequest = new PageInfoRequest(page);
        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(null);

        List<CoffeeReview> expectedReviews = capsuleReviews.subList(capsuleReviews.size() - REVIEW_PAGE_SIZE, capsuleReviews.size());
        Collections.reverse(expectedReviews);
        ProductReviewResponse expected = ProductMapper.supplyCoffeeReviewResponseFrom(
                new PageImpl<>(
                        expectedReviews, pageInfoRequest.getPageableWithSize(REVIEW_PAGE_SIZE), capsuleReviews.size()
                )
        );

        // when
        ProductReviewResponse actual = coffeeReviewService.getCoffeeReviews(coffee.getId(), new PageInfoRequest(page));

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("존재하지 않는 원두 아이디를 통해 조회할 시 예외를 던진다.")
    void Given_InvalidCapsuleId_When_GetCapsuleReviews_Then_ThrowException() {

        // given
        Long invalidId = coffee.getId();
        coffeeRepository.delete(coffee);

        Integer page = 0;
        PageInfoRequest pageInfoRequest = new PageInfoRequest(page);
        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(null);

        // when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_PRODUCT,
                assertThrows(BusinessException.class,
                        () -> coffeeReviewService.getCoffeeReviews(invalidId, pageInfoRequest)).getInfo()
        );

    }

}