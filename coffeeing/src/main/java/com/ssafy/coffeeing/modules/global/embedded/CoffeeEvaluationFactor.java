package com.ssafy.coffeeing.modules.global.embedded;

import javax.persistence.Embeddable;

@Embeddable
public class CoffeeEvaluationFactor {
	private Double roast;
	private Double acidity;
	private Double body;
}
