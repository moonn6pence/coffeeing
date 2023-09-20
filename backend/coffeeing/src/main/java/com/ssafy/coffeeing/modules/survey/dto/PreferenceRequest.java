package com.ssafy.coffeeing.modules.survey.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record PreferenceRequest(

        @NotNull Boolean isCapsule,
        @Range(min = 1, max = 5) Integer machineType,
        @Range(min = 0, max = 1) Double roast,
        @Range(min = 0, max = 1) Double acidity,
        @Range(min = 0, max = 1) Double body,
        @NotEmpty String flavorNote
) {
}
