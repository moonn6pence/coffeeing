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
@AttributeOverride(name = "id", column = @Column(name = "capsule_id"))
@Entity
public class Capsule extends BaseEntity {

    @Column
    private String brandKr;

    @Column
    private String brandEng;

    @Column
    private String capsuleNameKr;

    @Column
    private String capsuleNameEng;

    @Embedded
    private CoffeeCriteria coffeeCriteria;

    @Column
    private String aroma;

    @Column
    private Integer machineType;

    @Column
    private String imageUrl;

    @Column
    private String productDescription;

    @Column
    private Double totalScore;

    @Column
    private Integer totalReviewer;
}
