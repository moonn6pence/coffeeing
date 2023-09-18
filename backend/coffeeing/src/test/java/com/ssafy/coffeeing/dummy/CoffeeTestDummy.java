package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.global.embedded.CoffeeCriteria;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Profile("test")
public class CoffeeTestDummy {

    public static Coffee createMockCoffeeWiltonBenitezGeisha() {

        return Coffee.builder()
                .coffeeName("Wilton Benitez Geisha")
                .coffeeCriteria(new CoffeeCriteria(0.4, 0.9, 0.9))
                .aroma("floral, cocoa")
                .imageUrl("https://abcdsfadsffddd.png")
                .productDescription("")
                .totalScore(45.353)
                .totalReviewer(10)
                .build();
    }

    public static Coffee createMockCoffeeKenyaAA() {
        return Coffee.builder()
                .coffeeName("Kenya AA")
                .coffeeCriteria(new CoffeeCriteria(0.4, 0.9, 0.8))
                .aroma("sweet")
                .imageUrl("https://abcdsffddd.png")
                .productDescription("")
                .totalScore(50.0)
                .totalReviewer(10)
                .build();
    }

    public static List<Coffee> create25GeneralCoffees() {
        List<Coffee> coffees = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            coffees.add(Coffee.builder()
                    .coffeeName("Generic Coffee " + i)
                    .region("Generic Region " + i)
                    .coffeeCriteria(new CoffeeCriteria(0.4, 0.9, 0.8))
                    .aroma("Generic aroma")
                    .imageUrl("Generic Url " + i)
                    .productDescription("Generic Description")
                    .totalScore(60.0)
                    .totalReviewer(20)
                    .build()
            );
        }
        return coffees;
    }

    public static Coffee createMockCoffeeRoma(){
        return Coffee.builder()
                .coffeeName("로마 카프리치오")
                .coffeeCriteria(new CoffeeCriteria(0.4, 0.9, 0.8))
                .aroma("sweet")
                .imageUrl("https://abcdsffddd.png")
                .productDescription("")
                .totalScore(50.0)
                .totalReviewer(10)
                .build();
    }
}
