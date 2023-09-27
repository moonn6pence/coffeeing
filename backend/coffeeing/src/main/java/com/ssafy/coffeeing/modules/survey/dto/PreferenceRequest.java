package com.ssafy.coffeeing.modules.survey.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record PreferenceRequest(

        @NotNull Boolean isCapsule,
        @Range(min = 1, max = 5) Integer machineType,
        @NotNull @Range(min = 0, max = 1) Double roast,
        @NotNull @Range(min = 0, max = 1) Double acidity,
        @NotNull @Range(min = 0, max = 1) Double body,
        @NotNull @NotEmpty String flavorNote
) {
}
