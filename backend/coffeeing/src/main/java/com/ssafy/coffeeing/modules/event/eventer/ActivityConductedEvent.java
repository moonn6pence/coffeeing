package com.ssafy.coffeeing.modules.event.eventer;

import javax.validation.constraints.Min;

public record ActivityConductedEvent(
        @Min(0) int experience,
        long memberId
) {
}
