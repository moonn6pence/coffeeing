package com.ssafy.coffeeing.modules.product.event;

import com.ssafy.coffeeing.modules.product.service.CapsuleService;
import com.ssafy.coffeeing.modules.product.service.CoffeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class ProductEventListener {

    private final CapsuleService capsuleService;
    private final CoffeeService coffeeService;

    @TransactionalEventListener(value = ReviewCreatedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void reviewCreatedEventHandler(ReviewCreatedEvent event) {

        if (event.isCapsule()) {
            capsuleService.increasePopularity(event.id());
        } else {
            coffeeService.increasePopularity(event.id());
        }
    }

    @TransactionalEventListener(value = ReviewDeletedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void reviewDeletedEventHandler(ReviewDeletedEvent event) {

    }
}
