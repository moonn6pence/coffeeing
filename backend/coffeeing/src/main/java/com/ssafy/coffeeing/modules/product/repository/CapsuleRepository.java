package com.ssafy.coffeeing.modules.product.repository;

import com.ssafy.coffeeing.modules.product.domain.Capsule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapsuleRepository extends JpaRepository<Capsule, Long> {
}
