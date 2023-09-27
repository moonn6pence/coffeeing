package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.dummy.CapsuleReviewTestDummy;
import com.ssafy.coffeeing.dummy.CapsuleTestDummy;
import com.ssafy.coffeeing.dummy.MemberTestDummy;
import com.ssafy.coffeeing.modules.event.eventer.ExperienceEvent;
import com.ssafy.coffeeing.modules.global.dto.CreationResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.AuthErrorInfo;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleReview;
import com.ssafy.coffeeing.modules.product.dto.PageInfoRequest;
import com.ssafy.coffeeing.modules.product.dto.ProductReviewResponse;
import com.ssafy.coffeeing.modules.product.dto.ReviewRequest;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleReviewRepository;
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
class CapsuleReviewServiceTest extends ServiceTest {

    @Autowired
    private CapsuleReviewService capsuleReviewService;

    @Autowired
    private CapsuleRepository capsuleRepository;

    @Autowired
    private CapsuleReviewRepository capsuleReviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ApplicationEvents applicationEvents;

    private Capsule capsule;

    @BeforeEach
    void setUpCapsuleReviews() {
        capsule = capsuleRepository.save(CapsuleTestDummy.createMockCapsuleNapoli());
    }


    @Test
    @DisplayName("인증된 사용자가 캡슐 아이디와 리뷰 점수와 코멘트로 리뷰를 작성한다.")
    void Given_CapsuleIdAndReviewRequest_When_CreateReview_Then_Success() {

        // given
        ReviewRequest reviewRequest = new ReviewRequest(3, "tasty");
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        int expectedTotalScore = capsule.getTotalScore() + reviewRequest.score();
        int expectedTotalReviewer = capsule.getTotalReviewer() + 1;

        // when
        CreationResponse actual = capsuleReviewService.createReview(capsule.getId(), reviewRequest);

        // then
        CapsuleReview actualReview = capsuleReviewRepository.findById(actual.id())
                .orElseThrow(() -> new BusinessException(ProductErrorInfo.NOT_FOUND_REVIEW));

        assertAll(
                () -> assertEquals(capsule, actualReview.getCapsule()),
                () -> assertEquals(generalMember, actualReview.getMember()),
                () -> assertEquals(expectedTotalScore, capsule.getTotalScore()),
                () -> assertEquals(expectedTotalReviewer, capsule.getTotalReviewer()),
                () -> assertEquals(1, (int) applicationEvents.stream(ExperienceEvent.class).count())
        );
    }

    @Test
    @DisplayName("존재하지 않는 캡슐 아이디를 통해 리뷰를 생성할 시 예외를 던진다.")
    void Given_InvalidCapsuleId_When_CreateReview_Then_ThrowException() {

        // given
        Long invalidId = capsule.getId();
        capsuleRepository.delete(capsule);
        ReviewRequest reviewRequest = new ReviewRequest(3, "tasty");

        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_PRODUCT,
                assertThrows(BusinessException.class,
                        () -> capsuleReviewService.createReview(invalidId, reviewRequest)).getInfo()
        );

    }

    @Test
    @DisplayName("미인증 사용자가 캡슐 아이디와 페이지 번호를 통해 캡슐 리뷰를 조회한다.")
    void Given_CapsuleIdAndPageInfoRequest_When_GetCapsuleReviews_Then_Success() {

        // given
        List<Member> members = memberRepository.saveAll(MemberTestDummy.create25GeneralMembers());
        List<CapsuleReview> capsuleReviews =
                capsuleReviewRepository.saveAll(CapsuleReviewTestDummy.createMockCapsuleReviews(capsule, members));

        int page = 0;
        PageInfoRequest pageInfoRequest = new PageInfoRequest(page);
        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(null);

        List<CapsuleReview> expectedReviews = capsuleReviews.subList(capsuleReviews.size() - REVIEW_PAGE_SIZE, capsuleReviews.size());
        Collections.reverse(expectedReviews);
        ProductReviewResponse expected = ProductMapper.supplyCapsuleReviewResponseFrom(
                new PageImpl<>(
                        expectedReviews, pageInfoRequest.getPageableWithSize(REVIEW_PAGE_SIZE), capsuleReviews.size()
                )
        );

        // when
        ProductReviewResponse actual = capsuleReviewService.getReviews(capsule.getId(), new PageInfoRequest(page));

        // then
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("존재하지 않는 캡슐 아이디를 통해 조회할 시 예외를 던진다.")
    void Given_InvalidCapsuleId_When_GetCapsuleReviews_Then_ThrowException() {

        // given
        Long invalidId = capsule.getId();
        capsuleRepository.delete(capsule);

        int page = 0;
        PageInfoRequest pageInfoRequest = new PageInfoRequest(page);
        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(null);

        // when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_PRODUCT,
                assertThrows(BusinessException.class,
                        () -> capsuleReviewService.getReviews(invalidId, pageInfoRequest)).getInfo()
        );

    }

    @Test
    @DisplayName("인증된 사용자가 자신의 캡슐 리뷰 아이디와 리뷰 점수와 코멘트로 리뷰를 수정한다.")
    void Given_CapsuleReviewIdAndReviewRequest_When_UpdateReview_Then_Success() {

        // given
        ReviewRequest reviewRequest = new ReviewRequest(1, "disgusting");
        CapsuleReview review = CapsuleReview.builder()
                .capsule(capsule)
                .member(generalMember)
                .score(3)
                .content("tasty")
                .build();
        capsuleReviewRepository.save(review);
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when
        capsuleReviewService.updateReview(review.getId(), reviewRequest);

        // then
        assertAll(
                () -> assertEquals(review.getScore(), (int) reviewRequest.score()),
                () -> assertEquals(review.getContent(), reviewRequest.content())
        );
    }

    @Test
    @DisplayName("존재하지 않는 캡슐 리뷰 아이디로 리뷰 수정을 요청할 시 예외를 던진다.")
    void Given_InvalidCapsuleReviewId_When_UpdateReview_Then_ThrowException() {

        // given
        ReviewRequest reviewRequest = new ReviewRequest(1, "disgusting");
        CapsuleReview review = CapsuleReview.builder()
                .capsule(capsule)
                .member(generalMember)
                .score(3)
                .content("tasty")
                .build();
        capsuleReviewRepository.save(review);
        Long invalidId = review.getId();
        capsuleReviewRepository.delete(review);

        // when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_REVIEW,
                assertThrows(BusinessException.class,
                        () -> capsuleReviewService.updateReview(invalidId, reviewRequest)).getInfo()
        );

    }

    @Test
    @DisplayName("인증된 사용자가 타인의 리뷰 수정을 요청할 시 예외를 던진다.")
    void Given_CapsuleReviewIdOfOthers_When_UpdateReview_Then_ThrowException() {

        // given
        ReviewRequest reviewRequest = new ReviewRequest(1, "disgusting");

        Member other = memberRepository.save(MemberTestDummy.createGeneralMember("Sean", "{noop}seanjjang", "seanbryan@naver.com"));

        CapsuleReview review = CapsuleReview.builder()
                .capsule(capsule)
                .member(other)
                .score(3)
                .content("tasty")
                .build();
        capsuleReviewRepository.save(review);

        long reviewId = review.getId();

        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when, then
        BusinessException e = assertThrows(BusinessException.class,
                () -> capsuleReviewService.updateReview(reviewId, reviewRequest));
        assertEquals(AuthErrorInfo.UNAUTHORIZED, e.getInfo());
    }

    @Test
    @DisplayName("인증된 사용자가 캡슐 리뷰 아이디로 리뷰 삭제를 요청한다.")
    void Given_CapsuleReviewId_When_DeleteReview_Then_Success() {

        // given
        CapsuleReview review = CapsuleReview.builder()
                .capsule(capsule)
                .member(generalMember)
                .score(3)
                .content("tasty")
                .build();
        capsuleReviewRepository.save(review);
        capsule.addReview(review.getScore());
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        int expectedTotalScore = capsule.getTotalScore() - review.getScore();
        int expectedTotalReviewer = capsule.getTotalReviewer() - 1;

        // when
        capsuleReviewService.deleteReview(review.getId());

        // then
        assertAll(
                () -> assertNull(capsuleReviewRepository.findById(review.getId()).orElse(null)),
                () -> assertEquals(expectedTotalScore, capsule.getTotalScore()),
                () -> assertEquals(expectedTotalReviewer, capsule.getTotalReviewer())
        );
    }

    @Test
    @DisplayName("존재하지 않는 캡슐 리뷰 아이디로 리뷰 삭제를 요청할 시 예외를 던진다.")
    void Given_InvalidCapsuleReviewId_When_DeleteReview_Then_ThrowException() {

        // given
        CapsuleReview review = CapsuleReview.builder()
                .capsule(capsule)
                .member(generalMember)
                .score(3)
                .content("tasty")
                .build();
        capsuleReviewRepository.save(review);
        Long invalidId = review.getId();
        capsuleReviewRepository.delete(review);

        // when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_REVIEW,
                assertThrows(BusinessException.class,
                        () -> capsuleReviewService.deleteReview(invalidId)).getInfo()
        );

    }

    @Test
    @DisplayName("인증된 사용자가 타인의 캡슐 리뷰 삭제를 요청할 시 예외를 던진다.")
    void Given_CapsuleReviewIdOfOthers_When_DeleteReview_Then_Success() {

        // given
        Member other = memberRepository.save(MemberTestDummy
                .createGeneralMember("Sean", "{noop}seanjjang", "seanbryan@naver.com"));

        CapsuleReview review = CapsuleReview.builder()
                .capsule(capsule)
                .member(other)
                .score(3)
                .content("tasty")
                .build();
        capsuleReviewRepository.save(review);
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        long reviewId = review.getId();

        // when, then
        BusinessException e = assertThrows(BusinessException.class,
                () -> capsuleReviewService.deleteReview(reviewId));
        assertEquals(AuthErrorInfo.UNAUTHORIZED, e.getInfo());
    }
}