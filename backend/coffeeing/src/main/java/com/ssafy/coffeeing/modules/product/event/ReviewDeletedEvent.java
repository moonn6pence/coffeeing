package com.ssafy.coffeeing.modules.product.event;

public record ReviewDeletedEvent(
        Boolean isCapsule,
        Long id
) {
}
