package com.ssafy.coffeeing.modules.curation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurationType {
    CAPSULE_POPULAR(true, "HOT 캡슐 Top 10", null, null),
    COFFEE_POPULAR(false, "HOT 원두 Top 10", null, null),

    CAPSULE_PREFERENCE(true, "님에게 알맞는 캡슐", "preference", null),
    COFFEE_PREFERENCE(false, "님에게 알맞는 원두들", "preference", null),

    CAPSULE_ROAST_LIGHT(true, "로스팅이 약한 캡슐", "roast", "low"),
    CAPSULE_ROAST_DARK(true, "로스팅이 강한 캡슐", "roast", "high"),
    CAPSULE_BODY_LIGHT(true, "바디감이 약한 캡슐", "body", "low"),
    CAPSULE_BODY_HEAVY(true, "바디감이 강한 캡슐", "body", "high"),
    CAPSULE_ACIDITY_LOW(true, "산미가 약한 캡슐", "acidity", "low"),
    CAPSULE_ACIDITY_HIGH(true, "산미가 강한 캡슐", "acidity", "high"),
    CAPSULE_FLAVOR(true, "capsule essentials; mood ", "flavorNote", null),

    COFFEE_ROAST_LIGHT(false, "로스팅이 약한 원두", "roast", "low"),
    COFFEE_ROAST_DARK(false, "로스팅이 강한 원두", "roast", "high"),
    COFFEE_BODY_LIGHT(false, "바디감이 약한 원두", "body", "low"),
    COFFEE_BODY_HEAVY(false, "바디감이 강한 원두", "body", "high"),
    COFFEE_ACIDITY_LOW(false, "산미가 약한 원두", "acidity", "low"),
    COFFEE_ACIDITY_HIGH(false, "산미가 강한 원두", "acidity", "high"),
    COFFEE_FLAVOR(false, "coffee essentials; mood ", "flavorNote", null),

    CAPSULE_AGE_GENDER(true, null, "age and gender", null),
    COFFEE_AGE_GENDER(false, null, "age and gender", null),

    CAPSULE_LIKED_PRODUCT(true, " 스타일의 캡슐들", "liked capsule", null),
    COFFEE_LIKED_PRODUCT(false, " 스타일의 원두들", "liked capsule", null),;

    private final Boolean isCapsule;
    private final String title;
    private final String criteria;
    private final String attribute;

}