package com.ssafy.coffeeing.modules.global.embedded;

import javax.persistence.Embeddable;

@Embeddable
public class CoffeeCriteria {
	private Double roast;
	private Double acidity;
	private Double body;
}
