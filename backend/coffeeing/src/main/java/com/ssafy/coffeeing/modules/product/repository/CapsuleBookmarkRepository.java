package com.ssafy.coffeeing.modules.product.repository;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapsuleBookmarkRepository extends JpaRepository<CapsuleBookmark, Long> {
    Boolean existsByCapsuleAndMember(Capsule capsule, Member member);

    CapsuleBookmark findByCapsuleAndMember(Capsule capsule, Member member);
}
