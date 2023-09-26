package com.ssafy.coffeeing.modules.product.repository;

import com.ssafy.coffeeing.modules.product.domain.Capsule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapsuleRepository extends JpaRepository<Capsule, Long> {

    List<Capsule> findCapsulesByCapsuleNameKrContainingIgnoreCase(String keyword, Pageable pageable);

    List<Capsule> findTop10CapsulesByOrderByPopularityDesc();

    List<Capsule> findTop10ByFlavorNoteContains(String flavorNote);
}
