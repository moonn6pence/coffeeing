package com.ssafy.coffeeing.modules.search.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CapsuleSearchElement extends ProductSearchElement {
    String brandKr;

    String brandEng;
}
