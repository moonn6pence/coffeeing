package com.ssafy.coffeeing.modules.product.repository;


import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.dto.BookmarkedElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookmarkQueryRepository {

    Page<BookmarkedElement> findBookmarkedCoffeeElements(Member member, Pageable pageable);

    Page<BookmarkedElement> findBookmarkedCapsuleElements(Member member, Pageable pageable);
}
