package com.ssafy.coffeeing.modules.curation.mapper;

import com.ssafy.coffeeing.modules.curation.dto.CurationElement;
import com.ssafy.coffeeing.modules.curation.dto.CurationResponse;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurationMapper {

    public static CurationResponse supplyCurationResponseFrom(List<CurationElement> curations) {

        return new CurationResponse(curations);
    }

    public static CurationElement supplyCapsuleCurationElementOf(Boolean isCapsule, String title, List<Capsule> capsules) {

        return new CurationElement(title, isCapsule, capsules.stream().map(ProductMapper::supplySimpleProductElementFrom).toList());
    }

    public static CurationElement supplyCoffeeCurationElementOf(Boolean isCapsule, String title, List<Coffee> coffees) {

        return new CurationElement(title, isCapsule, coffees.stream().map(ProductMapper::supplySimpleProductElementFrom).toList());
    }

}
