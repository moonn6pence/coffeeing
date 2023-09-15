package com.ssafy.coffeeing.modules.feed.service;

import com.ssafy.coffeeing.dummy.FeedTestDummy;
import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.domain.FeedLike;
import com.ssafy.coffeeing.modules.feed.dto.*;
import com.ssafy.coffeeing.modules.feed.repository.FeedLikeRepository;
import com.ssafy.coffeeing.modules.feed.repository.FeedRepository;
import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.FeedErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

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
    private FeedLikeRepository feedLikeRepository;

    @MockBean
    private SecurityContextUtils securityContextUtils;

    @DisplayName("피드 업로드 요청 시, 업로드에 성공한다.")
    @Test
    void Given_UploadFeedRequest_When_SaveFeed_Then_Success() {
        //given
        given(securityContextUtils.getCurrnetAuthenticatedMember())
                .willReturn(generalMember);
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
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        //when, then
        feedService.deleteFeedById(feed.getId());

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("피드 내용 수정 요청 시, 수정에 성공한다.")
    @Test
    void Given_UpdateFeedRequest_When_UpdateFeed_Then_Success() {
        //given
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
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
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
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
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        UpdateFeedRequest updateFeedRequest = FeedTestDummy.createUpdateFeedRequest();
        given(securityContextUtils.getCurrnetAuthenticatedMember())
                .willReturn(beforeResearchMember);

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
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(beforeResearchMember);

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
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        given(securityContextUtils.getCurrnetAuthenticatedMember())
                .willReturn(beforeResearchMember);

        //when, then
        assertEquals(FeedErrorInfo.NOT_FOUND, assertThrows(BusinessException.class,
                () -> feedService.deleteFeedById(feed.getId())).getInfo());

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("피드 좋아요 클릭 시, 해당 유저가 좋아요를 누른 적이 없다면 좋아요가 생성된다.")
    @Test
    void Given_FeedLikeRequest_When_ToggleFeedLike_Then_Success() {
        //given
        given(securityContextUtils.getCurrnetAuthenticatedMember())
                .willReturn(generalMember);
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        Long beforeLikeCount = feed.getLikeCount();

        //when
        ToggleResponse toggleResponse = feedService.toggleFeedLike(feed.getId());
        Optional<FeedLike> result = feedLikeRepository.findFeedLikeByFeedAndMember(feed, generalMember);

        //then
        assertAll(
                () -> assertThat(toggleResponse.result()).isTrue(),
                () -> assertThat(feed.getLikeCount()).isEqualTo(beforeLikeCount + 1),
                () -> assertThat(result.isPresent()).isTrue()
        );

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("피드 좋아요 클릭 시, 해당 유저가 좋아요를 누른 적이 있다면 좋아요가 취소된다.")
    @Test
    void Given_FeedDisLikeRequest_When_ToggleFeedLike_Then_Success() {
        //given
        given(securityContextUtils.getCurrnetAuthenticatedMember())
                .willReturn(generalMember);
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        FeedLike feedLike = feedLikeRepository.save(FeedTestDummy.createFeedLike(feed, generalMember));
        feed.increaseLikeCount();
        Long beforeLikeCount = feed.getLikeCount();

        //when
        ToggleResponse toggleResponse = feedService.toggleFeedLike(feed.getId());
        Optional<FeedLike> result = feedLikeRepository.findById(feedLike.getId());

        //then
        assertAll(
                () -> assertThat(toggleResponse.result()).isFalse(),
                () -> assertThat(feed.getLikeCount()).isEqualTo(beforeLikeCount - 1),
                () -> assertThat(result.isEmpty()).isTrue()
        );

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("나의 피드 조회 시, 최대 10개의 피드들을 조회하는데 성공한다")
    @Test
    void Given_MyFeedsRequest_When_RequestMyFeeds_Then_Success() {
        //given
        given(securityContextUtils.getCurrnetAuthenticatedMember())
                .willReturn(generalMember);
        List<Feed> feeds = FeedTestDummy.createFeeds(generalMember);
        feedRepository.saveAll(feeds);
        MyFeedsRequest myFeedsRequest = FeedTestDummy.createMyFeedsRequest(null, null);

        //when
        ProfileFeedsResponse myFeedsResponse = feedService.getMyFeeds(myFeedsRequest);

        //then
        assertAll(
                () -> assertThat(myFeedsResponse.feeds().size()).isLessThanOrEqualTo(10)
        );

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("다른 멤버의 피드 조회 시, 최대 10개의 피드들을 조회하는데 성공한다.")
    @Test
    void Given_MemberFeedsRequest_When_RequestMemberFeeds_Then_Success() {
        //given
        List<Feed> feeds = FeedTestDummy.createFeeds(beforeResearchMember);
        MemberFeedsRequest memberFeedsRequest = FeedTestDummy
                .createMemberFeedsRequest(beforeResearchMember.getId(), null, null);
        feedRepository.saveAll(feeds);

        //when
        ProfileFeedsResponse profileFeedsResponse = feedService.getFeedsByMemberId(memberFeedsRequest);

        //then
        assertAll(
                () -> assertThat(profileFeedsResponse.feeds().size()).isLessThanOrEqualTo(10)
        );
    }
}