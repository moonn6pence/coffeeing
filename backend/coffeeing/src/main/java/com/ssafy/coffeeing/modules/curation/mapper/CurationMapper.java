package com.ssafy.coffeeing.modules.curation.mapper;

import com.ssafy.coffeeing.modules.curation.domain.CurationType;
import com.ssafy.coffeeing.modules.curation.dto.CurationElement;
import com.ssafy.coffeeing.modules.curation.dto.CurationResponse;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;

import java.util.List;

public class CurationMapper {

    public static CurationResponse supplyCurationResponseFrom(List<CurationElement> curations) {

        return new CurationResponse(curations);
    }

    public static CurationElement supplyCurationElementOf(Boolean isCapsule, String title, List<Capsule> capsules) {

        return new CurationElement(title, isCapsule, capsules.stream().map(ProductMapper::supplySimpleProductElementFrom).toList());
    }

}
