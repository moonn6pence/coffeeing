package com.ssafy.coffeeing.modules.product.repository;

import com.ssafy.coffeeing.modules.product.domain.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
