package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.dummy.CoffeeReviewTestDummy;
import com.ssafy.coffeeing.dummy.CoffeeTestDummy;
import com.ssafy.coffeeing.dummy.MemberTestDummy;
import com.ssafy.coffeeing.modules.event.eventer.ExperienceEvent;
import com.ssafy.coffeeing.modules.global.dto.CreationResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.AuthErrorInfo;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeReview;
import com.ssafy.coffeeing.modules.product.dto.PageInfoRequest;
import com.ssafy.coffeeing.modules.product.dto.ProductReviewResponse;
import com.ssafy.coffeeing.modules.product.dto.ReviewRequest;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeReviewRepository;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@RecordApplicationEvents
class CoffeeReviewServiceTest extends ServiceTest {

    @Autowired
    private CoffeeReviewService coffeeReviewService;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeReviewRepository coffeeReviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ApplicationEvents applicationEvents;

    private Coffee coffee;

    @BeforeEach
    void setUpCoffeeReviews() {
        coffee = coffeeRepository.save(CoffeeTestDummy.createMockCoffeeKenyaAA());
    }

    @Test
    @DisplayName("인증된 사용자가 캡슐 아이디와 리뷰 점수와 코멘트로 리뷰를 작성한다.")
    void Given_CoffeeIdAndReviewRequest_When_CreateReviews_Then_Success() {

        // given
        ReviewRequest reviewRequest = new ReviewRequest(3, "tasty");
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when
        CreationResponse actual = coffeeReviewService.createReview(coffee.getId(), reviewRequest);

        // then
        CoffeeReview actualReview = coffeeReviewRepository.findById(actual.id()).get();

        assertAll(
                () -> assertEquals(actualReview.getCoffee(), coffee),
                () -> assertEquals(actualReview.getMember(), generalMember)
        );
    }

    @Test
    @DisplayName("리뷰 작성시 경험치 이벤트가 발생한다.")
    void Given_CoffeeIdAndReviewRequest_When_CreateReviews_Then_ExperienceEventSuccess() {

        // given
        ReviewRequest reviewRequest = new ReviewRequest(3, "tasty");
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when
        CreationResponse actual = coffeeReviewService.createReview(coffee.getId(), reviewRequest);

        // then
        assertEquals(1, (int) applicationEvents.stream(ExperienceEvent.class).count());
    }

    @Test
    @DisplayName("존재하지 않는 캡슐 아이디를 통해 리뷰를 생성할 시 예외를 던진다.")
    void Given_InvalidCoffeeId_When_CreateReview_Then_ThrowException() {

        // given
        Long invalidId = coffee.getId();
        coffeeRepository.delete(coffee);
        ReviewRequest reviewRequest = new ReviewRequest(3, "tasty");

        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_PRODUCT,
                assertThrows(BusinessException.class,
                        () -> coffeeReviewService.createReview(invalidId, reviewRequest)).getInfo()
        );

    }


    @Test
    @DisplayName("미인증 사용자가 커피 아이디와 페이지 번호를 통해 캡슐 리뷰를 조회한다.")
    void Given_CoffeeIdAndPageInfoRequest_When_GetCoffeeReviews_Then_Success() {

        // given
        List<Member> members = memberRepository.saveAll(MemberTestDummy.create25GeneralMembers());
        List<CoffeeReview> coffeeReviews =
                coffeeReviewRepository.saveAll(CoffeeReviewTestDummy.createMockCoffeeReviews(coffee, members));

        Integer page = 0;
        PageInfoRequest pageInfoRequest = new PageInfoRequest(page);
        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(null);

        List<CoffeeReview> expectedReviews = coffeeReviews.subList(coffeeReviews.size() - REVIEW_PAGE_SIZE, coffeeReviews.size());
        Collections.reverse(expectedReviews);
        ProductReviewResponse expected = ProductMapper.supplyCoffeeReviewResponseFrom(
                new PageImpl<>(
                        expectedReviews, pageInfoRequest.getPageableWithSize(REVIEW_PAGE_SIZE), coffeeReviews.size()
                )
        );

        // when
        ProductReviewResponse actual = coffeeReviewService.getReviews(coffee.getId(), new PageInfoRequest(page));

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("존재하지 않는 원두 아이디를 통해 조회할 시 예외를 던진다.")
    void Given_InvalidCoffeeId_When_GetCoffeeReviews_Then_ThrowException() {

        // given
        Long invalidId = coffee.getId();
        coffeeRepository.delete(coffee);

        Integer page = 0;
        PageInfoRequest pageInfoRequest = new PageInfoRequest(page);
        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(null);

        // when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_PRODUCT,
                assertThrows(BusinessException.class,
                        () -> coffeeReviewService.getReviews(invalidId, pageInfoRequest)).getInfo()
        );

    }

    @Test
    @DisplayName("인증된 사용자가 자신의 캡슐 리뷰 아이디와 리뷰 점수와 코멘트로 리뷰를 수정한다.")
    void Given_CoffeeReviewIdAndReviewRequest_When_UpdateReview_Then_Success() {

        // given
        ReviewRequest reviewRequest = new ReviewRequest(1, "disgusting");
        CoffeeReview review = CoffeeReview.builder()
                .coffee(coffee)
                .member(generalMember)
                .score(3.5)
                .content("tasty")
                .build();
        coffeeReviewRepository.save(review);
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when
        coffeeReviewService.updateReview(review.getId(), reviewRequest);

        // then
        assertAll(
                () -> assertEquals(review.getScore(), (double) reviewRequest.score()),
                () -> assertEquals(review.getContent(), reviewRequest.content())
        );
    }

    @Test
    @DisplayName("존재하지 않는 캡슐 리뷰 아이디로 리뷰 수정을 요청할 시 예외를 던진다.")
    void Given_InvalidCoffeeReviewId_When_UpdateReview_Then_ThrowException() {

        // given
        ReviewRequest reviewRequest = new ReviewRequest(1, "disgusting");
        CoffeeReview review = CoffeeReview.builder()
                .coffee(coffee)
                .member(generalMember)
                .score(3.5)
                .content("tasty")
                .build();
        coffeeReviewRepository.save(review);
        Long invalidId = review.getId();
        coffeeReviewRepository.delete(review);

        // when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_REVIEW,
                assertThrows(BusinessException.class,
                        () -> coffeeReviewService.updateReview(invalidId, reviewRequest)).getInfo()
        );

    }

    @Test
    @DisplayName("인증된 사용자가 타인의 리뷰 수정을 요청할 시 예외를 던진다.")
    void Given_CoffeeReviewIdOfOthers_When_UpdateReview_Then_ThrowException() {

        // given
        ReviewRequest reviewRequest = new ReviewRequest(1, "disgusting");

        Member other = memberRepository.save(MemberTestDummy.createGeneralMember("Sean", "{noop}seanjjang", "seanbryan@naver.com"));

        CoffeeReview review = CoffeeReview.builder()
                .coffee(coffee)
                .member(other)
                .score(3.5)
                .content("tasty")
                .build();
        coffeeReviewRepository.save(review);

        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when, then
        assertEquals(AuthErrorInfo.UNAUTHORIZED,
                assertThrows(BusinessException.class,
                        () -> coffeeReviewService.updateReview(review.getId(), reviewRequest)).getInfo()
        );
    }

    @Test
    @DisplayName("인증된 사용자가 캡슐 리뷰 아이디로 리뷰 삭제를 요청한다.")
    void Given_CoffeeReviewId_When_DeleteReview_Then_Success() {

        // given
        CoffeeReview review = CoffeeReview.builder()
                .coffee(coffee)
                .member(generalMember)
                .score(3.5)
                .content("tasty")
                .build();
        coffeeReviewRepository.save(review);
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when
        coffeeReviewService.deleteReview(review.getId());

        // then
        assertNull(coffeeReviewRepository.findById(review.getId()).orElse(null));
    }

    @Test
    @DisplayName("존재하지 않는 캡슐 리뷰 아이디로 리뷰 삭제를 요청할 시 예외를 던진다.")
    void Given_InvalidCoffeeReviewId_When_DeleteReview_Then_ThrowException() {

        // given
        CoffeeReview review = CoffeeReview.builder()
                .coffee(coffee)
                .member(generalMember)
                .score(3.5)
                .content("tasty")
                .build();
        coffeeReviewRepository.save(review);
        Long invalidId = review.getId();
        coffeeReviewRepository.delete(review);

        // when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_REVIEW,
                assertThrows(BusinessException.class,
                        () -> coffeeReviewService.deleteReview(invalidId)).getInfo()
        );

    }

    @Test
    @DisplayName("인증된 사용자가 타인의 캡슐 리뷰 삭제를 요청할 시 예외를 던진다.")
    void Given_CoffeeReviewIdOfOthers_When_DeleteReview_Then_Success() {

        // given
        Member other = memberRepository.save(MemberTestDummy.createGeneralMember("Sean", "{noop}seanjjang", "seanbryan@naver.com"));


        CoffeeReview review = CoffeeReview.builder()
                .coffee(coffee)
                .member(other)
                .score(3.5)
                .content("tasty")
                .build();
        coffeeReviewRepository.save(review);
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when, then
        assertEquals(AuthErrorInfo.UNAUTHORIZED,
                assertThrows(BusinessException.class,
                        () -> coffeeReviewService.deleteReview(review.getId())).getInfo()
        );
    }
}