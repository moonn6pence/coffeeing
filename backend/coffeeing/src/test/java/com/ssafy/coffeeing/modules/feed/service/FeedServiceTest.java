package com.ssafy.coffeeing.modules.feed.service;

import com.ssafy.coffeeing.dummy.CapsuleTestDummy;
import com.ssafy.coffeeing.dummy.CoffeeTestDummy;
import com.ssafy.coffeeing.dummy.FeedTestDummy;
import com.ssafy.coffeeing.modules.event.eventer.ExperienceEvent;
import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.domain.FeedLike;
import com.ssafy.coffeeing.modules.feed.domain.FeedPage;
import com.ssafy.coffeeing.modules.feed.dto.*;
import com.ssafy.coffeeing.modules.feed.repository.FeedLikeRepository;
import com.ssafy.coffeeing.modules.feed.repository.FeedRepository;
import com.ssafy.coffeeing.modules.feed.util.FeedUtil;
import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.FeedErrorInfo;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.tag.domain.TagType;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RecordApplicationEvents
class FeedServiceTest extends ServiceTest {

    @Autowired
    private FeedService feedService;

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private FeedLikeRepository feedLikeRepository;

    @Autowired
    private CapsuleRepository capsuleRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @MockBean
    private SecurityContextUtils securityContextUtils;

    @Autowired
    private ApplicationEvents applicationEvents;

    @Autowired
    private FeedUtil feedUtil;

    @DisplayName("피드 업로드 요청 시, 업로드에 성공한다.")
    @Test
    void Given_UploadFeedRequest_When_SaveFeed_Then_Success() {
        //given
        given(securityContextUtils.getCurrnetAuthenticatedMember())
                .willReturn(generalMember);
        UploadFeedRequest uploadFeedRequest = FeedTestDummy.createUploadFeedRequestWithoutTag();

        //when
        UploadFeedResponse uploadFeedResponse = feedService
                .uploadFeedByMember(uploadFeedRequest);
        Feed response = feedRepository.getReferenceById(uploadFeedResponse.feedId());

        //then
        assertAll(
                () -> assertThat(response.getImageUrl()).isNotEqualTo(null),
                () -> assertThat(response.getContent()).isEqualTo(uploadFeedRequest.content()),
                () -> assertThat(response.getId()).isPositive(),
                () -> assertThat(response.getLikeCount()).isEqualTo(0L),
                () -> assertThat(response.getTagId()).isEqualTo(null),
                () -> assertThat(response.getTagType()).isEqualTo(null),
                () -> assertThat(response.getTagName()).isEqualTo(null)
        );

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("태그와 함께 피드 업로드 요청 시, 업로드에 성공한다.")
    @Test
    void GivenUploadFeeRequestWIthTag_When_SaveFeed_Then_Success() {
        //given
        given(securityContextUtils.getCurrnetAuthenticatedMember())
                .willReturn(generalMember);
        Capsule capsule = capsuleRepository.save(CapsuleTestDummy.createMockCapsuleRoma());
        UploadFeedRequest uploadFeedRequest = FeedTestDummy.createUploadFeedRequestWithTag(capsule);

        //when
        UploadFeedResponse uploadFeedResponse = feedService
                .uploadFeedByMember(uploadFeedRequest);
        Feed response = feedRepository.getReferenceById(uploadFeedResponse.feedId());

        //then
        assertAll(
                () -> assertThat(response.getImageUrl()).isNotEqualTo(null),
                () -> assertThat(response.getContent()).isEqualTo(uploadFeedRequest.content()),
                () -> assertThat(response.getId()).isPositive(),
                () -> assertThat(response.getLikeCount()).isEqualTo(0L),
                () -> assertThat(response.getTagId()).isEqualTo(capsule.getId()),
                () -> assertThat(response.getTagType()).isEqualTo(TagType.CAPSULE),
                () -> assertThat(response.getTagName()).isEqualTo(capsule.getCapsuleName())
        );

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @Test
    @DisplayName("피드 등록시 점수 증가 이벤트리스너가 실행된다.")
    void Given_UploadFeedRequest_When_SaveFeed_Then_EventListenerSuccess() {
        //given
        given(securityContextUtils.getCurrnetAuthenticatedMember())
                .willReturn(generalMember);
        UploadFeedRequest uploadFeedRequest = FeedTestDummy.createUploadFeedRequest();

        //when
        feedService.uploadFeedByMember(uploadFeedRequest);
        //then
        assertEquals(1, (int) applicationEvents.stream(ExperienceEvent.class).count());

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
        UpdateFeedRequest updateFeedRequest = FeedTestDummy.createUpdateFeedRequestWithoutTag();

        //when
        feedService.updateFeedById(feed.getId(), updateFeedRequest);

        //then
        assertThat(feed.getContent()).isEqualTo(updateFeedRequest.content());

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("태그와 함께 피드 업데이트 요청 시, 수정에 성공한다.")
    @Test
    void Given_UpdateFeedWithTag_When_UpdateFeed_Then_Success() {
        //given
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        Coffee coffee = coffeeRepository.save(CoffeeTestDummy.createMockCoffeeRoma());
        UpdateFeedRequest updateFeedRequest = FeedTestDummy.createUpdateFeedRequestWithTag(coffee);

        //when
        feedService.updateFeedById(feed.getId(), updateFeedRequest);

        //then
        assertAll(
                () -> assertThat(feed.getContent()).isEqualTo(updateFeedRequest.content()),
                () -> assertThat(feed.getTagName()).isEqualTo(updateFeedRequest.tag().name()),
                () -> assertThat(feed.getTagId()).isEqualTo(updateFeedRequest.tag().tagId()),
                () -> assertThat(feed.getTagType()).isEqualTo(updateFeedRequest.tag().category())
        );

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("태그와 함께 피드 업데이트 요청 시, 태그 정보가 존재하지 않다면 수정에 실패한다.")
    @Test
    void Given_UpdateFeedWithNotExistTag_When_UpdateFeed_Then_Fail() {
        //given
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        Coffee coffee = CoffeeTestDummy.createMockCoffeeRomaWithInvalidId();
        UpdateFeedRequest updateFeedRequest = FeedTestDummy.createUpdateFeedRequestWithTag(coffee);

        //when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_PRODUCT, assertThrows(BusinessException.class,
                () -> feedService.updateFeedById(feed.getId(), updateFeedRequest)).getInfo());


        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("피드 업데이트 요청 시, 존재하지 않는 피드 ID 라면 수정에 실패한다.")
    @Test
    void Given_UpdateFeed_With_InValidID_When_UpdateFeed_Then_Fail() {
        //given
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        UpdateFeedRequest updateFeedRequest = FeedTestDummy.createUpdateFeedRequestWithoutTag();

        //when, then
        assertEquals(FeedErrorInfo.NOT_FOUND, assertThrows(BusinessException.class,
                () -> feedService.updateFeedById(feed.getId() + 1, updateFeedRequest)).getInfo());

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("피드 업데이트 요청 시, 피드의 작성자가 아니라면 수정에 실패한다.")
    @Test
    void Given_UpdateFeed_With_InValidMember_When_UpdateFeed_Then_Fail() {
        //given
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        UpdateFeedRequest updateFeedRequest = FeedTestDummy.createUpdateFeedRequestWithoutTag();
        given(securityContextUtils.getCurrnetAuthenticatedMember())
                .willReturn(beforeResearchMember);

        //when, then
        assertEquals(FeedErrorInfo.NOT_FOUND, assertThrows(BusinessException.class,
                () -> feedService.updateFeedById(feed.getId(), updateFeedRequest)).getInfo());

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
        FeedsRequest feedsRequest = FeedTestDummy.createFeedsRequest(null, null);
        feeds = feeds.stream().sorted(Comparator.comparing(Feed::getId).reversed())
                .collect(Collectors.toList());
        List<FeedElement> expectFeedElements = feeds.stream()
                .map(feed -> new FeedElement(feed.getId(), feedUtil.makeJsonStringToImageElement(feed.getImageUrl())))
                .collect(Collectors.toList());

        //when
        ProfileFeedsResponse profileFeedsResponse = feedService.getMyFeeds(feedsRequest);

        //then
        assertThat(profileFeedsResponse.feeds().size()).isLessThanOrEqualTo(10);
        assertThat(expectFeedElements.subList(0, profileFeedsResponse.feeds().size()))
                .usingRecursiveComparison().isEqualTo(profileFeedsResponse.feeds());

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
        feeds = feeds.stream().sorted(Comparator.comparing(Feed::getId).reversed())
                .collect(Collectors.toList());
        List<FeedElement> expectFeedElements = feeds.stream()
                .map(feed -> new FeedElement(feed.getId(), feedUtil.makeJsonStringToImageElement(feed.getImageUrl())))
                .collect(Collectors.toList());

        //when
        ProfileFeedsResponse profileFeedsResponse = feedService.getFeedsByMemberId(memberFeedsRequest);

        //then
        assertThat(profileFeedsResponse.feeds().size()).isLessThanOrEqualTo(10);
        assertThat(expectFeedElements.subList(0, profileFeedsResponse.feeds().size()))
                .usingRecursiveComparison().isEqualTo(profileFeedsResponse.feeds());
    }

    @DisplayName("피드 상세보기 조회 시, 피드 ID가 존재한다면 조회에 성공한다.")
    @Test
    void Given_FeedId_When_Request_FeedDetail_Then_Success() {
        //given
        Feed feed = FeedTestDummy.createFeed(generalMember);
        feedRepository.save(feed);

        //when
        FeedDetailResponse feedDetailResponse = feedService.getFeedDetailById(feed.getId());

        //then
        assertAll(
                () -> assertEquals(feedDetailResponse.feedId(), feed.getId()),
                () -> assertEquals(feedDetailResponse.likeCount(), feed.getLikeCount()),
                () -> assertEquals(feedDetailResponse.content(), feed.getContent()),
                () -> assertEquals(feedDetailResponse.images(), feedUtil.makeJsonStringToImageElement(feed.getImageUrl())),
                () -> assertEquals(feedDetailResponse.registerId(), feed.getMember().getId()),
                () -> assertEquals(feedDetailResponse.registerName(), feed.getMember().getNickname()),
                () -> assertEquals(feedDetailResponse.registerProfileImg(), feed.getMember().getProfileImage()),
                () -> assertNull(feedDetailResponse.tag()),
                () -> assertEquals(feedDetailResponse.isLike(), false),
                () -> assertEquals(feedDetailResponse.isMine(), false)
        );
    }

    @DisplayName("회원이 좋아요를 눌렀던 피드 상세보기 조회 시, 피드 ID가 존재한다면 조회에 성공한다.")
    @Test
    void Given_FeedIdWithFeedLikedMember_When_Request_FeedDetail_Then_Success() {
        //given
        Feed feed = FeedTestDummy.createFeed(generalMember);
        FeedLike feedLike = FeedTestDummy.createFeedLike(feed, beforeResearchMember);
        feedRepository.save(feed);
        feedLikeRepository.save(feedLike);
        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(beforeResearchMember);

        //when
        FeedDetailResponse feedDetailResponse = feedService.getFeedDetailById(feed.getId());

        //then
        assertAll(
                () -> assertEquals(feedDetailResponse.feedId(), feed.getId()),
                () -> assertEquals(feedDetailResponse.likeCount(), feed.getLikeCount()),
                () -> assertEquals(feedDetailResponse.content(), feed.getContent()),
                () -> assertEquals(feedDetailResponse.images(), feedUtil.makeJsonStringToImageElement(feed.getImageUrl())),
                () -> assertEquals(feedDetailResponse.registerId(), feed.getMember().getId()),
                () -> assertEquals(feedDetailResponse.registerName(), feed.getMember().getNickname()),
                () -> assertEquals(feedDetailResponse.registerProfileImg(), feed.getMember().getProfileImage()),
                () -> assertEquals(feedDetailResponse.isLike(), true),
                () -> assertEquals(feedDetailResponse.isMine(), false)
        );
        //verify
        verify(securityContextUtils, times(1)).getMemberIdByTokenOptionalRequest();
    }

    @DisplayName("토큰값을 통해 피드 페이지를 조회 시, 최대 10개의 피드들을 불러오는데 성공한다.")
    @Test
    void GivenToken_When_Request_FeedPage_Then_Success() {
        //given
        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(generalMember);
        FeedsRequest feedsRequest = FeedTestDummy.createFeedsRequest(null, null);
        List<Feed> feeds = FeedTestDummy.createFeeds(beforeResearchMember);
        List<FeedLike> feedLikes = new ArrayList<>();
        feeds.forEach(feed -> feedLikes.add(FeedLike.builder().feed(feed).member(generalMember).build()));
        feedRepository.saveAll(feeds);
        feedLikeRepository.saveAll(feedLikes);
        feeds = feeds.stream().sorted(Comparator.comparing(Feed::getId).reversed())
                .collect(Collectors.toList());
        FeedPage expectResponse = new FeedPage(feeds.subList(0, 10), feedLikes, generalMember, feedUtil);

        //when
        FeedPageResponse feedPageResponse = feedService.getFeedsByFeedPage(feedsRequest);

        //then
        assertAll(
                () -> assertThat(feedPageResponse.feeds().size()).isLessThanOrEqualTo(10),
                () -> assertThat(expectResponse.feedPageElements).usingRecursiveComparison().isEqualTo(feedPageResponse.feeds())
        );

        //verify
        verify(securityContextUtils, times(1)).getMemberIdByTokenOptionalRequest();
    }

    @DisplayName("토큰 값이 존재하지 않을때 피드 페이지 조회 시, 최대 10개의 피드들을 불러오는데 성공한다.")
    @Test
    void GivenNotToken_When_Request_FeedPage_Then_Success() {
        //given
        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(generalMember);
        List<Feed> feeds = FeedTestDummy.createFeeds(beforeResearchMember);
        List<FeedLike> feedLikes = new ArrayList<>();
        feedRepository.saveAll(feeds);
        FeedsRequest feedsRequest = FeedTestDummy.createFeedsRequest(null, null);
        feeds = feeds.stream().sorted(Comparator.comparing(Feed::getId).reversed())
                .collect(Collectors.toList());
        FeedPage expectResponse = new FeedPage(feeds.subList(0, 10), feedLikes, generalMember, feedUtil);

        //when
        FeedPageResponse feedPageResponse = feedService.getFeedsByFeedPage(feedsRequest);

        //then
        assertAll(
                () -> assertThat(feedPageResponse.feeds().size()).isLessThanOrEqualTo(10),
                () -> assertThat(expectResponse.feedPageElements).usingRecursiveComparison().isEqualTo(feedPageResponse.feeds())
        );

        //verify
        verify(securityContextUtils, times(1)).getMemberIdByTokenOptionalRequest();
    }
}