package com.ssafy.coffeeing.modules.feed.scheduler;

import com.ssafy.coffeeing.modules.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FeedScheduler {

    private final FeedService feedService;

    @Scheduled(fixedDelay = 14100000)
    public void writeBackFeedLikeInRedis() {
        feedService.writeBackFeedLikeInRedis();
    }
}
