package com.ssafy.coffeeing.modules.feed.service;

import com.ssafy.coffeeing.dummy.CapsuleTestDummy;
import com.ssafy.coffeeing.dummy.CoffeeTestDummy;
import com.ssafy.coffeeing.dummy.FeedTestDummy;
import com.ssafy.coffeeing.modules.event.eventer.ExperienceEvent;
import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.domain.FeedLike;
import com.ssafy.coffeeing.modules.feed.domain.FeedPage;
import com.ssafy.coffeeing.modules.feed.dto.FeedDetailResponse;
import com.ssafy.coffeeing.modules.feed.dto.FeedElement;
import com.ssafy.coffeeing.modules.feed.dto.FeedPageResponse;
import com.ssafy.coffeeing.modules.feed.dto.FeedsRequest;
import com.ssafy.coffeeing.modules.feed.dto.MemberFeedsRequest;
import com.ssafy.coffeeing.modules.feed.dto.ProfileFeedsResponse;
import com.ssafy.coffeeing.modules.feed.dto.UpdateFeedRequest;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedRequest;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedResponse;
import com.ssafy.coffeeing.modules.feed.repository.FeedLikeRepository;
import com.ssafy.coffeeing.modules.feed.repository.FeedRepository;
import com.ssafy.coffeeing.modules.feed.util.FeedRedisUtil;
import com.ssafy.coffeeing.modules.feed.util.FeedUtil;
import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.FeedErrorInfo;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.ProductType;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
    private FeedRedisUtil feedRedisUtil;

    @Autowired
    private CapsuleRepository capsuleRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

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
                () -> assertThat(response.getLikeCount()).isEqualTo(0),
                () -> assertThat(response.getTagId()).isEqualTo(null),
                () -> assertThat(response.getProductType()).isEqualTo(null),
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
        Integer previousPopularity = capsule.getPopularity();

        //when
        UploadFeedResponse uploadFeedResponse = feedService
                .uploadFeedByMember(uploadFeedRequest);
        Feed response = feedRepository.getReferenceById(uploadFeedResponse.feedId());

        //then
        assertAll(
                () -> assertThat(response.getImageUrl()).isNotEqualTo(null),
                () -> assertThat(response.getContent()).isEqualTo(uploadFeedRequest.content()),
                () -> assertThat(response.getId()).isPositive(),
                () -> assertThat(response.getLikeCount()).isEqualTo(0),
                () -> assertThat(response.getTagId()).isEqualTo(capsule.getId()),
                () -> assertThat(response.getProductType()).isEqualTo(ProductType.COFFEE_CAPSULE),
                () -> assertThat(response.getTagName()).isEqualTo(capsule.getCapsuleNameKr()),
                () -> assertThat(capsule.getPopularity()).isEqualTo(previousPopularity + 10)
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
        UploadFeedRequest uploadFeedRequest = FeedTestDummy.createUploadFeedRequestWithoutTag();

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
        feedLikeRepository.save(FeedTestDummy.createFeedLike(feed, generalMember));
        feedLikeRepository.save(FeedTestDummy.createFeedLike(feed, beforeResearchMember));
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        long feedId = feed.getId();

        //when
        em.flush();
        feedService.deleteFeedById(feedId);

        //then
        assertThat(feedRepository.findById(feed.getId()).isEmpty()).isTrue();
        assertThat(feedLikeRepository.findFeedLikeByFeedAndMember(feed, generalMember).isEmpty()).isTrue();

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("피드 삭제 요청 시, 태그가 존재하던 피드라면 태그와 연관된 Product Popularity 가 10 감소한다.")
    @Test
    void Given_DeleteFeedRequest_When_DeleteFeedAndDetachTag_Then_Success() {
        //given
        given(securityContextUtils.getCurrnetAuthenticatedMember())
                .willReturn(generalMember);
        Capsule capsule = capsuleRepository.save(CapsuleTestDummy.createMockCapsuleRoma());
        UploadFeedResponse uploadFeedResponse = feedService.uploadFeedByMember(FeedTestDummy.createUploadFeedRequestWithTag(capsule));
        Feed feed = feedRepository.getReferenceById(uploadFeedResponse.feedId());
        Integer previousPopularity = capsule.getPopularity();
        long feedId = feed.getId();

        //when
        em.flush();
        feedService.deleteFeedById(feedId);

        //then
        assertThat(feedRepository.findById(feed.getId()).isEmpty()).isTrue();
        assertThat(capsule.getPopularity()).isEqualTo(previousPopularity - 10);

        //verify
        verify(securityContextUtils, times(2)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("피드 내용 수정 요청 시, 수정에 성공한다.")
    @Test
    void Given_UpdateFeedRequest_When_UpdateFeed_Then_Success() {
        //given
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        UpdateFeedRequest updateFeedRequest = FeedTestDummy.createUpdateFeedRequestWithoutTag();
        long feedId = feed.getId();

        //when
        feedService.updateFeedById(feedId, updateFeedRequest);
        em.flush();

        //then
        assertThat(feed.getContent()).isEqualTo(updateFeedRequest.content());

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("태그와 함께 태그가 없던 피드 업데이트 요청 시, 수정에 성공한다.")
    @Test
    void Given_UpdateFeedWithTag_When_UpdateFeed_Then_Success() {
        //given
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        Coffee coffee = coffeeRepository.save(CoffeeTestDummy.createMockCoffeeRoma());
        Integer previousPopularity = coffee.getPopularity();
        UpdateFeedRequest updateFeedRequest = FeedTestDummy.createUpdateFeedRequestWithTag(coffee);
        long feedId = feed.getId();

        //when
        feedService.updateFeedById(feedId, updateFeedRequest);
        em.flush();

        //then
        assertAll(
                () -> assertThat(feed.getContent()).isEqualTo(updateFeedRequest.content()),
                () -> assertThat(feed.getTagName()).isEqualTo(updateFeedRequest.tag().name()),
                () -> assertThat(feed.getTagId()).isEqualTo(updateFeedRequest.tag().tagId()),
                () -> assertThat(feed.getProductType()).isEqualTo(updateFeedRequest.tag().category()),
                () -> assertThat(coffee.getPopularity()).isEqualTo(previousPopularity + 10)
        );

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("태그와 함께 태그가 있던 피드 업데이트 요청 시, 피드 업데이트에 성공한다.")
    @Test
    void Given_UpdateFeedWithChangeTag_When_UpdateFeed_Then_Success() {
        //given
        Coffee coffee = coffeeRepository.save(CoffeeTestDummy.createMockCoffeeKenyaAA());
        Feed feed = feedRepository.save(FeedTestDummy.createFeedWithCoffeeTag(generalMember, coffee));
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        Capsule capsule = capsuleRepository.save(CapsuleTestDummy.createMockCapsuleRoma());
        UpdateFeedRequest updateFeedRequest = FeedTestDummy.createUpdateFeedRequestWithTag(capsule);
        Integer previousCoffeePopularity = coffee.getPopularity();
        Integer previousCapsulePopularity = capsule.getPopularity();
        long feedId = feed.getId();

        //when
        feedService.updateFeedById(feedId, updateFeedRequest);
        em.flush();

        //then
        assertAll(
                () -> assertThat(feed.getContent()).isEqualTo(updateFeedRequest.content()),
                () -> assertThat(feed.getTagName()).isEqualTo(updateFeedRequest.tag().name()),
                () -> assertThat(feed.getTagId()).isEqualTo(updateFeedRequest.tag().tagId()),
                () -> assertThat(feed.getProductType()).isEqualTo(updateFeedRequest.tag().category()),
                () -> assertThat(coffee.getPopularity()).isEqualTo(previousCoffeePopularity - 10),
                () -> assertThat(capsule.getPopularity()).isEqualTo(previousCapsulePopularity + 10)
        );

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("태그와 함께 피드 업데이트 요청 시, 태그 정보가 존재하지 않다면 수정에 실패한다.")
    @Test
    void Given_UpdateFeedWithNotExistTag_When_UpdateFeed_Then_Fail() {
        //given
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        long feedId = feed.getId();
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);
        Coffee coffee = CoffeeTestDummy.createMockCoffeeRomaWithInvalidId();
        UpdateFeedRequest updateFeedRequest = FeedTestDummy.createUpdateFeedRequestWithTag(coffee);

        //when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_PRODUCT, assertThrows(BusinessException.class,
                () -> feedService.updateFeedById(feedId, updateFeedRequest)).getInfo());


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
        long feedId = feed.getId();
        //when, then
        assertEquals(FeedErrorInfo.NOT_FOUND, assertThrows(BusinessException.class,
                () -> feedService.updateFeedById(feedId + 1, updateFeedRequest)).getInfo());

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
        long feedId = feed.getId();

        //when, then
        assertEquals(FeedErrorInfo.NOT_FOUND, assertThrows(BusinessException.class,
                () -> feedService.updateFeedById(feedId, updateFeedRequest)).getInfo());

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }

    @DisplayName("피드 삭제 요청 시, 존재하지 않는 피드 ID 라면 삭제에 실패한다.")
    @Test
    void Given_DeleteRequest_When_DeleteFeed_Then_Fail() {
        //given
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        long feedId = feed.getId();
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(beforeResearchMember);

        //when, then
        assertEquals(FeedErrorInfo.NOT_FOUND, assertThrows(BusinessException.class,
                () -> feedService.deleteFeedById(feedId + 1)).getInfo());

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
        long feedId = feed.getId();

        //when, then
        assertEquals(FeedErrorInfo.NOT_FOUND, assertThrows(BusinessException.class,
                () -> feedService.deleteFeedById(feedId)).getInfo());

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
        int beforeLikeCount = feedRedisUtil.getFeedLikeCount(feed);
        long feedId = feed.getId();

        //when
        ToggleResponse toggleResponse = feedService.toggleFeedLike(feedId);
        Boolean isLikeFeed = feedRedisUtil.isLikedFeedInRedis(feed, generalMember);

        //then
        assertAll(
                () -> assertThat(toggleResponse.result()).isTrue(),
                () -> assertThat(feedRedisUtil.getFeedLikeCount(feed)).isEqualTo(beforeLikeCount + 1),
                () -> assertThat(isLikeFeed).isTrue()
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
        feedRedisUtil.likeFeedInRedis(feed, generalMember);
        feedRedisUtil.increaseLikeCount(feed);
        long feedId = feed.getId();
        int beforeLikeCount = feedRedisUtil.getFeedLikeCount(feed);

        //when
        ToggleResponse toggleResponse = feedService.toggleFeedLike(feedId);
        Boolean isNotLikeFeed = feedRedisUtil.isNotLikedFeedInRedis(feed, generalMember);

        //then
        assertAll(
                () -> assertThat(toggleResponse.result()).isFalse(),
                () -> assertThat(feedRedisUtil.getFeedLikeCount(feed)).isEqualTo(beforeLikeCount - 1),
                () -> assertThat(isNotLikeFeed).isTrue()
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
                .map(feed -> {
                    long feedId = feed.getId();
                    String imageUrl = feed.getImageUrl();
                    return new FeedElement(feedId, feedUtil.makeJsonStringToImageElement(imageUrl));
                })
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
                .map(feed -> {
                    long feedId = feed.getId();
                    String imageUrl = feed.getImageUrl();
                    return new FeedElement(feedId, feedUtil.makeJsonStringToImageElement(imageUrl));
                })
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
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        long feedId = feed.getId();

        //when
        FeedDetailResponse feedDetailResponse = feedService.getFeedDetailById(feedId);

        //then
        assertAll(
                () -> assertEquals(feed.getId(), feedDetailResponse.feedId()),
                () -> assertEquals(feed.getLikeCount(), feedDetailResponse.likeCount()),
                () -> assertEquals(feed.getContent(), feedDetailResponse.content()),
                () -> assertEquals(feedUtil.makeJsonStringToImageElement(feed.getImageUrl()), feedDetailResponse.images()),
                () -> assertEquals(feed.getMember().getId(), feedDetailResponse.registerId()),
                () -> assertEquals(feed.getMember().getNickname(), feedDetailResponse.registerName()),
                () -> assertEquals(feed.getMember().getProfileImage(), feedDetailResponse.registerProfileImg()),
                () -> assertNull(feedDetailResponse.tag()),
                () -> assertEquals(false, feedDetailResponse.isLike()),
                () -> assertEquals(false, feedDetailResponse.isMine())
        );
    }

    @DisplayName("회원이 좋아요를 눌렀던 피드 상세보기 조회 시, 피드 ID가 존재한다면 조회에 성공한다.")
    @Test
    void Given_FeedIdWithFeedLikedMember_When_Request_FeedDetail_Then_Success() {
        //given
        Feed feed = feedRepository.save(FeedTestDummy.createFeed(generalMember));
        feedLikeRepository.save(FeedTestDummy.createFeedLike(feed, beforeResearchMember));
        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(beforeResearchMember);
        long feedId = feed.getId();

        //when
        FeedDetailResponse feedDetailResponse = feedService.getFeedDetailById(feedId);

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
        FeedPage expectResponse = new FeedPage(feeds.subList(0, 10), feedRedisUtil, generalMember, feedUtil);

        //when
        FeedPageResponse feedPageResponse = feedService.getFeedsByFeedPage(feedsRequest);

        //then
        assertAll(
                () -> assertThat(feedPageResponse.feeds().size()).isLessThanOrEqualTo(10),
                () -> assertThat(expectResponse.getFeedPageElements()).usingRecursiveComparison().isEqualTo(feedPageResponse.feeds())
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
        feedRepository.saveAll(feeds);
        FeedsRequest feedsRequest = FeedTestDummy.createFeedsRequest(null, null);
        feeds = feeds.stream().sorted(Comparator.comparing(Feed::getId).reversed())
                .collect(Collectors.toList());
        FeedPage expectResponse = new FeedPage(feeds.subList(0, 10), feedRedisUtil, generalMember, feedUtil);

        //when
        FeedPageResponse feedPageResponse = feedService.getFeedsByFeedPage(feedsRequest);

        //then
        assertAll(
                () -> assertThat(feedPageResponse.feeds().size()).isLessThanOrEqualTo(10),
                () -> assertThat(expectResponse.getFeedPageElements()).usingRecursiveComparison().isEqualTo(feedPageResponse.feeds())
        );

        //verify
        verify(securityContextUtils, times(1)).getMemberIdByTokenOptionalRequest();
    }
}