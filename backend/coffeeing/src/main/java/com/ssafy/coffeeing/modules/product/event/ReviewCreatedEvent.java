package com.ssafy.coffeeing.modules.product.event;

public record ReviewCreatedEvent(
        Boolean isCapsule,
        Long id
) {
}
