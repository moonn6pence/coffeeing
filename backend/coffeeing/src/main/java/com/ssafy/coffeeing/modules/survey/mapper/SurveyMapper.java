package com.ssafy.coffeeing.modules.survey.mapper;

import com.ssafy.coffeeing.modules.global.embedded.CoffeeCriteria;
import com.ssafy.coffeeing.modules.product.domain.ProductType;
import com.ssafy.coffeeing.modules.survey.domain.Preference;
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import org.springframework.stereotype.Component;

@Component
public class SurveyMapper {

    public static Preference supplyPreferenceOf(PreferenceRequest p, Long memberId) {

        return Preference.builder()
                .productType(p.isCapsule() ? ProductType.COFFEE_CAPSULE : ProductType.COFFEE_BEAN)
                .machineType(p.isCapsule() ? p.machineType() : null)
                .coffeeCriteria(new CoffeeCriteria(p.roast() / 3.0, p.acidity() / 3.0, p.body() / 3.0))
                .flavorNote(p.flavorNote())
                .build();
    }
}