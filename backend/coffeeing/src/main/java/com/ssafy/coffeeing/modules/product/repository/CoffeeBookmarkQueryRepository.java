package com.ssafy.coffeeing.modules.product.repository;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.dto.BookmarkedElement;
import com.ssafy.coffeeing.modules.product.dto.SimpleProductElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CoffeeBookmarkQueryRepository {
    Page<SimpleProductElement> findBookmarkedCoffeeElements(Member member, Pageable pageable);

}
