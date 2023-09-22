package com.ssafy.coffeeing.modules.util;

import com.ssafy.coffeeing.modules.curation.domain.CurationType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class RandomUtil {

    private final List<CurationType> capsuleCharacteristicCurations;
    private final List<CurationType> coffeeCharacteristicCurations;
    private final List<String> flavors;

    private final Random random;

    public RandomUtil() {
        this.capsuleCharacteristicCurations = new ArrayList<>();
        this.coffeeCharacteristicCurations = new ArrayList<>();
        this.flavors = new ArrayList<>();
        this.random = new Random();

        this.capsuleCharacteristicCurations.add(CurationType.CAPSULE_ACIDITY_HIGH);
        this.capsuleCharacteristicCurations.add(CurationType.CAPSULE_ACIDITY_LOW);
        this.capsuleCharacteristicCurations.add(CurationType.CAPSULE_BODY_HEAVY);
        this.capsuleCharacteristicCurations.add(CurationType.CAPSULE_BODY_LIGHT);
        this.capsuleCharacteristicCurations.add(CurationType.CAPSULE_ROAST_DARK);
        this.capsuleCharacteristicCurations.add(CurationType.CAPSULE_ROAST_LIGHT);
        this.capsuleCharacteristicCurations.add(CurationType.CAPSULE_FLAVOR);

        this.capsuleCharacteristicCurations.add(CurationType.COFFEE_ACIDITY_HIGH);
        this.capsuleCharacteristicCurations.add(CurationType.COFFEE_ACIDITY_LOW);
        this.capsuleCharacteristicCurations.add(CurationType.COFFEE_BODY_HEAVY);
        this.capsuleCharacteristicCurations.add(CurationType.COFFEE_BODY_LIGHT);
        this.capsuleCharacteristicCurations.add(CurationType.COFFEE_ROAST_DARK);
        this.capsuleCharacteristicCurations.add(CurationType.COFFEE_ROAST_LIGHT);
        this.coffeeCharacteristicCurations.add(CurationType.CAPSULE_FLAVOR);

        this.flavors.add("fruity");
        this.flavors.add("sweety");
        this.flavors.add("chocolaty");
        this.flavors.add("nutty");
        this.flavors.add("spicy");
        this.flavors.add("floral");
    }

    public int generate(int boundary) {
        return random.nextInt(boundary);
    }

    public CurationType getRandomCharacteristicCuration(Boolean isCapsule) {

        if (isCapsule) {
            return capsuleCharacteristicCurations.get(generate(capsuleCharacteristicCurations.size()));
        }

        return coffeeCharacteristicCurations.get(generate(coffeeCharacteristicCurations.size()));
    }

    public String getRandomFlavor() {
        return flavors.get(generate(flavors.size()));
    }
}
