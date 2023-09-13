package com.ssafy.coffeeing.modules.feed.repository;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
