package com.ssafy.coffeeing.modules.util;

import com.ssafy.coffeeing.modules.curation.domain.CurationType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class RandomUtil {

    private final List<CurationType> capsuleCharacteristicCurations;
    private final List<CurationType> coffeeCharacteristicCurations;
    private final List<CurationType> capsuleKeywordCurations;
    private final List<CurationType> coffeeKeywordCurations;
    private final List<String> flavors;

    private final Random random;

    private static final String REGEX_IS_CRITERIA = "(roast|body|acidity|flavor)";
    private static final String REGEX_IS_KEYWORD = "(liked product|age and gender)";

    public RandomUtil() {
        this.capsuleCharacteristicCurations = new ArrayList<>();
        this.coffeeCharacteristicCurations = new ArrayList<>();
        this.capsuleKeywordCurations = new ArrayList<>();
        this.coffeeKeywordCurations = new ArrayList<>();
        this.flavors = List.of("fruity", "sweety", "chocolaty", "nutty", "spicy", "floral");
        this.random = new Random();

        Arrays.stream(CurationType.values())
                .forEach(curationType -> {
                    if (curationType.getIsCapsule().equals(Boolean.TRUE)) {
                        if (curationType.getCriteria().matches(REGEX_IS_CRITERIA)) {
                            this.capsuleCharacteristicCurations.add(curationType);
                        } else if (curationType.getCriteria().matches(REGEX_IS_KEYWORD)){
                            this.capsuleKeywordCurations.add(curationType);
                        }
                    } else {
                        if (curationType.getCriteria().matches(REGEX_IS_CRITERIA)) {
                            this.coffeeCharacteristicCurations.add(curationType);
                        } else if (curationType.getCriteria().matches(REGEX_IS_KEYWORD)) {
                            this.coffeeKeywordCurations.add(curationType);
                        }
                    }
                });
    }

    public int generate(int boundary) {
        return random.nextInt(boundary);
    }

    public CurationType getRandomCharacteristicCuration(Boolean isCapsule) {

        if (isCapsule.equals(Boolean.TRUE)) {
            return capsuleCharacteristicCurations.get(generate(capsuleCharacteristicCurations.size()));
        }

        return coffeeCharacteristicCurations.get(generate(coffeeCharacteristicCurations.size()));
    }

    public CurationType getRandomKeywordCuration(Boolean isCapsule) {

        if (isCapsule.equals(Boolean.TRUE)) {
            return capsuleKeywordCurations.get(generate(capsuleKeywordCurations.size()));
        }

        return coffeeCharacteristicCurations.get(generate(coffeeKeywordCurations.size()));
    }

    public String getRandomFlavor() {
        return flavors.get(generate(flavors.size()));
    }

}
