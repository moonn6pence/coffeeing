package com.ssafy.coffeeing.modules.global.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeCriteria {
	private Double roast;
	private Double acidity;
	private Double body;
}
