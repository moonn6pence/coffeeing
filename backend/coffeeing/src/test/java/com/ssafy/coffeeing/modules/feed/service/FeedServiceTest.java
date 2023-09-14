package com.ssafy.coffeeing.modules.feed.service;

import com.ssafy.coffeeing.dummy.FeedTestDummy;
import com.ssafy.coffeeing.dummy.MemberTestDummy;
import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.dto.UpdateFeedRequest;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedRequest;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedResponse;
import com.ssafy.coffeeing.modules.feed.repository.FeedRepository;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.FeedErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class FeedServiceTest extends ServiceTest {

    @Autowired
    private FeedService feedService;

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private SecurityContextUtils securityContextUtils;

    @DisplayName("피드 업로드 요청 시, 업로드에 성공한다.")
    @Test
    void Given_UploadFeedRequest_When_SaveFeed_Then_Success() {
        //given
        Member member = memberRepository.save(MemberTestDummy
                .createGeneralMember("testNickname", "test123", "test1@test.com"));
        given(securityContextUtils.getCurrnetAuthenticatedMember())
                .willReturn(member);
        UploadFeedRequest uploadFeedRequest = FeedTestDummy.createUploadFeedRequest();

        //when
        UploadFeedResponse uploadFeedResponse = feedService
                .uploadFeedByMember(uploadFeedRequest);
        Feed response = feedRepository.getReferenceById(uploadFeedResponse.feedId());

        //then
        assertAll(
                () -> assertThat(response.getImageUrl()).isNotEqualTo(null),
                () -> assertThat(response.getContent()).isEqualTo(uploadFeedRequest.content()),
                () -> assertThat(response.getId()).isPositive(),
                () -> assertThat(response.getLikeCount()).isEqualTo(0L)
        );

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }


    @DisplayName("피드 삭제 요청 시, 삭제에 성공한다.")
    @Test
    void Given_DeleteFeedRequest_When_DeleteFeed_Then_Success() {
        //given
        Member member = memberRepository.save(MemberTestDummy
                .createGeneralMember("paul", "test", "test2@naver.com"));
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(member));
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(member);

        //when, then
        feedService.deleteFeedById(feed.getId());

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("피드 내용 수정 요청 시, 수정에 성공한다.")
    @Test
    void Given_UpdateFeedRequest_When_UpdateFeed_Then_Success() {
        //given
        Member member = memberRepository.save(MemberTestDummy
                .createGeneralMember("testNickname", "123", "amico@naver.com"));
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(member));
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(member);
        UpdateFeedRequest updateFeedRequest = FeedTestDummy.createUpdateFeedRequest();

        //when
        feedService.updateFeedContentById(feed.getId(), updateFeedRequest);

        //then
        assertThat(feed.getContent()).isEqualTo(updateFeedRequest.content());

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("피드 업데이트 요청 시, 존재하지 않는 피드 ID 라면 수정에 실패한다.")
    @Test
    void Given_UpdateFeed_With_InValidID_When_UpdateFeed_Then_Fail() {
        //given
        Member member = memberRepository.save(MemberTestDummy
                .createGeneralMember("paul", "test1", "amico1@test.com"));
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(member));
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(member);
        UpdateFeedRequest updateFeedRequest = FeedTestDummy.createUpdateFeedRequest();

        //when, then
        assertEquals(FeedErrorInfo.NOT_FOUND, assertThrows(BusinessException.class,
                () -> feedService.updateFeedContentById(feed.getId() + 1, updateFeedRequest)).getInfo());

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("피드 업데이트 요청 시, 피드의 작성자가 아니라면 수정에 실패한다.")
    @Test
    void Given_UpdateFeed_With_InValidMember_When_UpdateFeed_Then_Fail() {
        //given
        Member member = memberRepository.save(MemberTestDummy
                .createGeneralMember("james", "testPassword", "test1@naver.com"));
        Member other = memberRepository.save(MemberTestDummy
                .createBeforeResearchMember("paul", "testPassword", "zase@naver.com"));
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(member));
        UpdateFeedRequest updateFeedRequest = FeedTestDummy.createUpdateFeedRequest();
        given(securityContextUtils.getCurrnetAuthenticatedMember())
                .willReturn(other);

        //when, then
        assertEquals(FeedErrorInfo.NOT_FOUND, assertThrows(BusinessException.class,
                () -> feedService.updateFeedContentById(feed.getId(), updateFeedRequest)).getInfo());

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("피드 삭제 요청 시, 존재하지 않는 피드 ID 라면 삭제에 실패한다.")
    @Test
    void Given_DeleteRequest_When_DeleteFeed_Then_Fail() {
        //given
        Member member = memberRepository.save(MemberTestDummy
                .createGeneralMember("shawn", "testPassword", "test10@naver.com"));
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(member));
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(member);

        //when, then
        assertEquals(FeedErrorInfo.NOT_FOUND, assertThrows(BusinessException.class,
                () -> feedService.deleteFeedById(feed.getId() + 1)).getInfo());

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("피드 삭제 요청 시, 피드의 작성자가 아니라면 삭제에 실패한다.")
    @Test
    void Given_DeleteRequest_With_InvalidMember_When_DeleteFeed_Then_Fail() {
        //given
        Member member = memberRepository.save(MemberTestDummy
                .createGeneralMember("shawnMendes", "srerwe", "test1@naver.com"));
        Member other = memberRepository.save(MemberTestDummy
                .createBeforeResearchMember("wony", "zserwe", "test2@naver.com"));
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(member));
        given(securityContextUtils.getCurrnetAuthenticatedMember())
                .willReturn(other);

        //when, then
        assertEquals(FeedErrorInfo.NOT_FOUND, assertThrows(BusinessException.class,
                () -> feedService.deleteFeedById(feed.getId())).getInfo());

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }
}