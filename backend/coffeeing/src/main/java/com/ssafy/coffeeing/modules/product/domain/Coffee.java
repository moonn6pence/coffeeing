package com.ssafy.coffeeing.modules.product.domain;

import com.ssafy.coffeeing.modules.global.embedded.CoffeeCriteria;
import com.ssafy.coffeeing.modules.util.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "coffee_id"))
@Entity
public class Coffee extends BaseEntity {

    @Column(length = 64, nullable = false)
    private String coffeeName;

    @Embedded
    private CoffeeCriteria coffeeCriteria;

    @Column
    private String aroma;
    
    @Column(columnDefinition = "text")
    private String imagePath;

    @Column(columnDefinition = "text")
    private String description;

    @Column
    private Double totalScore;

    @Column
    private Integer totalReviewer;

    @OneToMany(mappedBy = "coffee", targetEntity = CoffeeReview.class, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CoffeeReview> coffeeReview=new ArrayList<>();

}
