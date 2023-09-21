package com.ssafy.coffeeing.modules.search.repository;

import com.ssafy.coffeeing.modules.search.domain.Acidity;
import com.ssafy.coffeeing.modules.search.domain.Body;
import com.ssafy.coffeeing.modules.search.domain.Roast;
import com.ssafy.coffeeing.modules.search.dto.ProductSearchElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchDynamicRepository {
    Page<ProductSearchElement> searchByBeanConditions(List<Roast> roasts,
                                                      List<Acidity> acidities,
                                                      List<Body> bodies,
                                                      String flavorNote,
                                                      Pageable pageable);

    Page<ProductSearchElement> searchByCapsuleConditions(List<Roast> roasts,
                                            List<Acidity> acidities,
                                            List<Body> bodies,
                                            String flavorNote,
                                            Pageable pageable);
}
