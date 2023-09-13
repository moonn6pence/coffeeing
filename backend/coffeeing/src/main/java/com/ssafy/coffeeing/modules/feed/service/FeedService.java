package com.ssafy.coffeeing.modules.feed.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.dto.ImageElement;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedRequest;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedResponse;
import com.ssafy.coffeeing.modules.feed.mapper.FeedMapper;
import com.ssafy.coffeeing.modules.feed.repository.FeedRepository;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.FeedErrorInfo;
import com.ssafy.coffeeing.modules.global.exception.info.MemberErrorInfo;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public UploadFeedResponse uploadFeedByMember(UploadFeedRequest uploadFeedRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(MemberErrorInfo.NOT_FOUND));

        try{
            String imageUrl = objectMapper.writeValueAsString(uploadFeedRequest.images());
            Feed feed = FeedMapper.supplyFeedEntityBy(member, uploadFeedRequest.content(), imageUrl);
            return FeedMapper.supplyFeedResponseBy(feedRepository.save(feed));
        } catch (JsonProcessingException e) {
            throw new BusinessException(FeedErrorInfo.FEED_IMAGES_TO_JSON_STRING_ERROR);
        }
    }
}
