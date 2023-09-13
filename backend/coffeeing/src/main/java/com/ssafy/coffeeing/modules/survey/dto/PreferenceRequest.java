package com.ssafy.coffeeing.modules.survey.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public record PreferenceRequest(

        Boolean isCapsule,
        @Min(value = 1) @Max(value = 5) Integer machineType,
        @Min(value = 0) @Max(value = 3) Integer roast,
        @Min(value = 0) @Max(value = 3) Integer acidity,
        @Min(value = 0) @Max(value = 9) Integer flavorNote,
        @Min(value = 0) @Max(value = 3) Integer body1,
        @Min(value = 0) @Max(value = 3) Integer body2
) {
}
