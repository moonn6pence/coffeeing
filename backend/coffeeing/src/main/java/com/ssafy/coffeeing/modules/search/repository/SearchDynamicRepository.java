package com.ssafy.coffeeing.modules.search.repository;

import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.search.domain.Acidity;
import com.ssafy.coffeeing.modules.search.domain.Body;
import com.ssafy.coffeeing.modules.search.domain.Roast;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface SearchDynamicRepository {
    Slice<Coffee> searchByBeanConditions(List<Roast> roasts, List<Acidity> acidities, List<Body> bodies, String flavorNote,
                                         Integer page, Integer size);

    Slice<Capsule> searchByCapsuleConditions(List<Roast> roasts, List<Acidity> acidities, List<Body> bodies, String flavorNote,
                                             Integer page, Integer size);
}
