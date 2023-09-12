package com.ssafy.coffeeing.modules.global.embedded;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class CoffeeCriteria {
	private Double roast;
	private Double acidity;
	private Double body;
}
