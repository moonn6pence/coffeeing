package com.ssafy.coffeeing.modules.product.repository;

import com.ssafy.coffeeing.modules.product.domain.Coffee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    List<Coffee> findCoffeesByCoffeeNameKrContainingIgnoreCase(String keyword, Pageable pageable);

    List<Coffee> findTop10CapsulesByOrderByPopularityDesc();
}
