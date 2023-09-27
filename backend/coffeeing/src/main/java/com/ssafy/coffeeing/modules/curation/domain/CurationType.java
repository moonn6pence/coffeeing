package com.ssafy.coffeeing.modules.curation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurationType {
    CAPSULE_POPULAR(true, "HOT 캡슐 Top 10", CriteriaType.POPULARITY.getName(), null),
    COFFEE_POPULAR(false, "HOT 원두 Top 10", CriteriaType.POPULARITY.getName(), null),

    CAPSULE_PREFERENCE(true, "님에게 알맞는 캡슐", CriteriaType.PREFERENCE.getName(), null),
    COFFEE_PREFERENCE(false, "님에게 알맞는 원두들", CriteriaType.PREFERENCE.getName(), null),

    CAPSULE_ROAST_LIGHT(true, "로스팅이 약한 캡슐", CriteriaType.ROAST.getName(), "low"),
    CAPSULE_ROAST_DARK(true, "로스팅이 강한 캡슐", CriteriaType.ROAST.getName(), "high"),
    CAPSULE_BODY_LIGHT(true, "바디감이 약한 캡슐", CriteriaType.BODY.getName(), "low"),
    CAPSULE_BODY_HEAVY(true, "바디감이 강한 캡슐", CriteriaType.BODY.getName(), "high"),
    CAPSULE_ACIDITY_LOW(true, "산미가 약한 캡슐", CriteriaType.ACIDITY.getName(), "low"),
    CAPSULE_ACIDITY_HIGH(true, "산미가 강한 캡슐", CriteriaType.ACIDITY.getName(), "high"),
    CAPSULE_FLAVOR(true, "capsule essentials; mood ", CriteriaType.FLAVOR.getName(), null),

    COFFEE_ROAST_LIGHT(false, "로스팅이 약한 원두", CriteriaType.ROAST.getName(), "low"),
    COFFEE_ROAST_DARK(false, "로스팅이 강한 원두", CriteriaType.ROAST.getName(), "high"),
    COFFEE_BODY_LIGHT(false, "바디감이 약한 원두", CriteriaType.BODY.getName(), "low"),
    COFFEE_BODY_HEAVY(false, "바디감이 강한 원두", CriteriaType.BODY.getName(), "high"),
    COFFEE_ACIDITY_LOW(false, "산미가 약한 원두", CriteriaType.ACIDITY.getName(), "low"),
    COFFEE_ACIDITY_HIGH(false, "산미가 강한 원두", CriteriaType.ACIDITY.getName(), "high"),
    COFFEE_FLAVOR(false, "coffee essentials; mood ", CriteriaType.FLAVOR.getName(), null),

    CAPSULE_AGE_GENDER(true, null, CriteriaType.AGE_GENDER.getName(), null),
    COFFEE_AGE_GENDER(false, null, CriteriaType.AGE_GENDER.getName(), null),

    CAPSULE_LIKED_PRODUCT(true, " 스타일의 캡슐들", CriteriaType.LIKED_PRODUCT.getName(), null),
    COFFEE_LIKED_PRODUCT(false, " 스타일의 원두들", CriteriaType.LIKED_PRODUCT.getName(), null),;

    private final Boolean isCapsule;
    private final String title;
    private final String criteria;
    private final String attribute;

}