package com.ssafy.coffeeing.modules.survey.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public record PreferenceRequest(

        Boolean isCapsule,
        Integer machineType,
        @Min(value = 0) @Max(value = 3) Integer roast,
        @Min(value = 0) @Max(value = 3) Integer acidity,
        @Min(value = 0) @Max(value = 3) Integer body,
        @NotEmpty String flavorNote
) {
}
