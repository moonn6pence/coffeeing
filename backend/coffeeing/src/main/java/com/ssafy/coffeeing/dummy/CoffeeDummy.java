package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.global.embedded.CoffeeCriteria;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile("dev")
@RequiredArgsConstructor
@Component
public class CoffeeDummy {

    private final List<String> coffeeNames = List.of("Kenya AA", "Kenya AB", "Geisha", "아르페지오", "로마");
    private final List<Double> roasts = List.of(1.0, 1.0, 0.8, 0.8, 0.6);
    private final List<Double> acidities = List.of(1.0, 1.0, 0.8, 0.8, 0.6);
    private final List<Double> bodies = List.of(0.2, 0.2, 0.6, 0.4, 0.8);
    private final List<String> aroma = List.of("roasted, intense dark roasted", "spicy, dark roasted, woody", "dark roasted", "intense, cocoa", "woody, grain");

    private final List<String> imageUrls = List.of(
            "https://sample.image1.png",
            "https://sample.image2.png",
            "https://sample.image3.png",
            "https://sample.image4.png",
            "https://sample.image5.png"
    );
    private final List<String> productDescriptions = List.of(
            "sample description 1",
            "sample description 2",
            "sample description 3",
            "sample description 4",
            "sample description 5"
    );
    private final List<Double> totalScores = List.of(38.9, 48.3, 42.1, 49.8, 25.5);

    private final List<String> regions = List.of(
            "South america",
            "Costa Rica",
            "Ethiopia",
            "Colombia",
            "India"
    );

    private final CoffeeRepository coffeeRepository;

    public List<Coffee> create5CoffeeDummy(){

        List<Coffee> coffees = new ArrayList<>();

        for (int i = 0; i < coffeeNames.size(); i++) {
            coffees.add(createCoffee(coffeeNames.get(i), aroma.get(i),
                    imageUrls.get(i), productDescriptions.get(i),
                    new CoffeeCriteria(roasts.get(i), acidities.get(i), bodies.get(i)),
                    totalScores.get(i), 5,regions.get(i)));
        }

        return coffeeRepository.saveAll(coffees);
    }

    private Coffee createCoffee(String coffeeName, String aroma, String imageUrl, String productDescription,
                                CoffeeCriteria coffeeCriteria, Double totalScore ,Integer totalReviewer, String region){
        return Coffee.builder()
                .coffeeName(coffeeName)
                .coffeeCriteria(coffeeCriteria)
                .aroma(aroma)
                .imageUrl(imageUrl)
                .productDescription(productDescription)
                .totalScore(totalScore)
                .totalReviewer(totalReviewer)
                .regionEng(region)
                .build();
    }
}
