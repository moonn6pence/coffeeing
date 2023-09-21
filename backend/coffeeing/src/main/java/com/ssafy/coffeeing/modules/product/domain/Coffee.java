package com.ssafy.coffeeing.modules.product.domain;

import com.ssafy.coffeeing.modules.global.embedded.CoffeeCriteria;
import com.ssafy.coffeeing.modules.util.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "coffee_id"))
@Entity
public class Coffee extends BaseEntity {

    @Column
    private String regionKr;

    @Column
    private String regionEng;

    @Column
    private String coffeeNameKr;

    @Column
    private String coffeeNameEng;

    @Embedded
    private CoffeeCriteria coffeeCriteria;

    @Column
    private String aroma;
    
    @Column(columnDefinition = "text")
    private String imageUrl;

    @Column(columnDefinition = "text")
    private String productDescription;

    @Column
    private Double totalScore;

    @Column
    private Integer totalReviewer;



}
