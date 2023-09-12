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
@AttributeOverride(name = "id", column = @Column(name = "capsule_id"))
@Entity
public class Capsule extends BaseEntity {

    @Column
    private String brandKr;

    @Column
    private String brandEng;

    @Column
    private String capsuleName;

    @Embedded
    private CoffeeCriteria coffeeCriteria;

    @Column
    private String aroma;

    @Column
    private Integer machineType;

    @Column
    private String imageUrl;

    @Column
    private String description;

    @Column
    private Double totalScore;

    @Column
    private Integer totalReviewer;

    @OneToMany(mappedBy = "capsule", fetch = FetchType.LAZY)
    @Builder.Default
    private List<CapsuleReview> capsuleReviews = new ArrayList<>();
}
