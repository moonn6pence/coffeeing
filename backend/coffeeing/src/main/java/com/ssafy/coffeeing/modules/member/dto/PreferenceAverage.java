package com.ssafy.coffeeing.modules.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PreferenceAverage {
    private Double roast;
    private Double acidity;
    private Double body;
}
