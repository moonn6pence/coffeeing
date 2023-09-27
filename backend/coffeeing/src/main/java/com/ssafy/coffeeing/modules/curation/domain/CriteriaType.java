package com.ssafy.coffeeing.modules.curation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CriteriaType {

    ROAST("roast"),
    BODY("body"),
    ACIDITY("acidity"),
    POPULARITY("popularity"),
    PREFERENCE("preference"),
    FLAVOR("flavorNote"),
    AGE_GENDER("age and gender"),
    LIKED_PRODUCT("liked product");

    private String name;
}
