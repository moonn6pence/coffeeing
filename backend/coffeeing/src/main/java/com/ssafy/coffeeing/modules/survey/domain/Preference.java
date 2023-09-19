package com.ssafy.coffeeing.modules.survey.domain;

import com.ssafy.coffeeing.modules.global.embedded.CoffeeCriteria;
import com.ssafy.coffeeing.modules.product.domain.ProductType;
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import com.ssafy.coffeeing.modules.util.base.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "preference_id"))
@Entity
public class Preference extends BaseEntity {

	@Column(nullable = false)
	private Long memberId;

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private ProductType productType;

	@Column
	private Integer machineType;

	@Embedded
	private CoffeeCriteria coffeeCriteria;

	@Column
	private String flavorNote;

	public void update(PreferenceRequest p) {
		this.productType = p.isCapsule() ? ProductType.COFFEE_CAPSULE : ProductType.COFFEE_BEAN;
		this.machineType = p.isCapsule() ? p.machineType() : null;
		this.coffeeCriteria = new CoffeeCriteria(p.roast() / 3.0, p.acidity() / 3.0, p.body() / 3.0);
		this.flavorNote = p.flavorNote();
	}
}
