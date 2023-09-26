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
                .coffeeNameKr("윌튼 베니테즈 게이샤")
                .coffeeNameEng("Wilton Benitez Geisha")
                .regionEng("CostaRica")
                .regionKr("코스타리카")
                .coffeeCriteria(new CoffeeCriteria(0.4, 0.9, 0.9))
                .flavorNote("floral, cocoa")
                .imageUrl("https://abcdsfadsffddd.png")
                .productDescription("")
                .totalScore(45.353)
                .totalReviewer(10)
                .build();
    }

    public static Coffee createMockCoffeeKenyaAA() {
        return Coffee.builder()
                .coffeeNameKr("케냐 에이에이")
                .coffeeNameEng("Kenya AA")
                .regionKr("케냐")
                .regionEng("kenya")
                .coffeeCriteria(new CoffeeCriteria(0.4, 0.9, 0.8))
                .flavorNote("sweet")
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
                    .coffeeNameKr("제네렉 커피 " + i)
                    .coffeeNameEng("Generic Coffee " + i)
                    .regionKr("제네렉 지역 " + i)
                    .regionEng("Generic Region " + i)
                    .coffeeCriteria(new CoffeeCriteria(0.4, 0.9, 0.8))
                    .flavorNote("Generic aroma")
                    .imageUrl("Generic Url " + i)
                    .productDescription("Generic Description")
                    .totalScore(60.0)
                    .totalReviewer(20)
                    .build()
            );
        }
        return coffees;
    }

    public static List<Coffee> create10SpecifiedFlavorCoffees(String flavorNote) {
        List<Coffee> coffees = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            coffees.add(Coffee.builder()
                    .coffeeNameKr("제네렉 커피 " + i)
                    .coffeeNameEng("Generic Coffee " + i)
                    .regionKr("제네렉 지역 " + i)
                    .regionEng("Generic Region " + i)
                    .coffeeCriteria(new CoffeeCriteria(0.4, 0.9, 0.8))
                    .flavorNote(flavorNote)
                    .imageUrl("Generic Url " + i)
                    .productDescription("Generic Description")
                    .totalScore(81.2 + i)
                    .totalReviewer(20)
                    .popularity(i * 10)
                    .build()
            );
        }
        return coffees;
    }

    public static Coffee createMockCoffeeRoma() {
        return Coffee.builder()
                .coffeeNameKr("로마 카프리치오")
                .coffeeNameEng("Roma cappricio")
                .regionKr("로마")
                .regionEng("ROMA")
                .coffeeCriteria(new CoffeeCriteria(0.4, 0.9, 0.8))
                .flavorNote("sweet")
                .imageUrl("https://abcdsffddd.png")
                .productDescription("")
                .totalScore(50.0)
                .totalReviewer(10)
                .build();
    }

    public static Coffee createMockCoffeeRomaWithInvalidId() {
        return Coffee.builder()
                .id(-1L)
                .coffeeNameKr("로마 카프리치오")
                .coffeeNameEng("Roma cappricio")
                .regionKr("로마")
                .regionEng("ROMA")
                .coffeeCriteria(new CoffeeCriteria(0.4, 0.9, 0.8))
                .flavorNote("sweet")
                .imageUrl("https://abcdsffddd.png")
                .productDescription("")
                .totalScore(50.0)
                .totalReviewer(10)
                .build();
    }
}
