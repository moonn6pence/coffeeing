package com.ssafy.coffeeing.dummy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.dto.ImageElement;
import com.ssafy.coffeeing.modules.feed.repository.FeedRepository;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.FeedErrorInfo;
import com.ssafy.coffeeing.modules.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile("dev")
@RequiredArgsConstructor
@Component
public class FeedDummy {

    private final FeedRepository feedRepository;
    private final List<String> feedContents = new ArrayList<>(){
        {
            add(null);
            add("커피는 항상 옳다");
            add("아침은 커피로 시작");
            add("네스프레소가 짱이다");
            add("돌체라떼 먹고 싶다");
            add("밥먹고 커피가 생각나");
            add("카페인 없이 못산다");
            add("캡슐 커피는 진짜 최애");
            add("새로운 커피 추천해주세요");
            add("카페인 수혈");
            add("샷 2개가 국룰");
            add("IllY COFFEE");
            add("원두로 먹는 것도 좋아");
        }
    };

    public List<Feed> createFeedDummies(List<Member> members) {
        List<Feed> feeds = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String feedImageUrlAsString = objectMapper.writeValueAsString(
                            new ImageElement("https://coffeeing.s3.ap-northeast-2.amazonaws.com/beanPicture.webp"));
            for(int i = 0; i < members.size(); i++) {
                for (String feedContent : feedContents) {

                    feeds.add(Feed.builder()
                            .member(members.get(i))
                            .content(i + feedContent)
                            .imageUrl(feedImageUrlAsString)
                            .likeCount(0L)
                            .build());
                }
            }

            feedRepository.saveAll(feeds);
            return feeds;
        } catch (JsonProcessingException e) {
            throw new BusinessException(FeedErrorInfo.FEED_IMAGES_TO_JSON_STRING_ERROR);
        }
    }
}
