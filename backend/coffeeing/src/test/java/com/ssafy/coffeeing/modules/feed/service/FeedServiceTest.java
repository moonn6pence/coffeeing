package com.ssafy.coffeeing.modules.feed.service;

import com.ssafy.coffeeing.dummy.FeedTestDummy;
import com.ssafy.coffeeing.dummy.MemberTestDummy;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedRequest;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedResponse;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class FeedServiceTest extends ServiceTest {

    @Autowired
    private FeedService feedService;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private SecurityContextUtils securityContextUtils;

    @DisplayName("피드 업로드 요청 시, 업로드에 성공한다.")
    @Test
    void Given_UploadFeedRequest_When_SaveFeed_Then_Success() {
        //given
        Member member = memberRepository.save(MemberTestDummy.createGeneralMember());
        given(securityContextUtils.getCurrnetAuthenticatedMember())
                .willReturn(member);
        UploadFeedRequest uploadFeedRequest = FeedTestDummy.createUploadFeedRequest();

        //when
        UploadFeedResponse uploadFeedResponse = feedService
                .uploadFeedByMember(uploadFeedRequest);

        //then
        assertThat(uploadFeedResponse.feedId()).isPositive();

        //verify
        verify(securityContextUtils, times(1)).getCurrnetAuthenticatedMember();
    }
}