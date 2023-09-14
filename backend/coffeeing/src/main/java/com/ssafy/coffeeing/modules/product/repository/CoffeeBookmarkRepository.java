package com.ssafy.coffeeing.modules.product.repository;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeBookmarkRepository extends JpaRepository<CoffeeBookmark, Long> {

    Boolean existsByCoffeeAndMember(Coffee coffee, Member member);

    CoffeeBookmark findByCoffeeAndMember(Coffee coffee, Member member);
}
