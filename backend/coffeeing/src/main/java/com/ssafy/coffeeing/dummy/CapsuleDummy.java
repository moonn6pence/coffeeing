package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.global.embedded.CoffeeCriteria;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile("dev")
@RequiredArgsConstructor
@Component
public class CapsuleDummy {

    private final List<String> capsuleNames = List.of("나폴리", "카자르", "리스트레토", "아르페지오", "로마");
    private final List<Double> roasts = List.of(1.0, 1.0, 0.8, 0.8, 0.6);
    private final List<Double> acidities = List.of(1.0, 1.0, 0.8, 0.8, 0.6);
    private final List<Double> bodies = List.of(0.2, 0.2, 0.6, 0.4, 0.8);
    private final List<String> aroma = List.of("roasted, intense dark roasted", "spicy, dark roasted, woody", "dark roasted", "intense, cocoa", "woody, grain");

    private final List<String> imageUrls = List.of(
            "https://www.nespresso.com/ecom/medias/sys_master/public/16987597701150/ispirazione-napoli-2x.png?impolicy=small&imwidth=284&imdensity=1",
            "https://www.nespresso.com/ecom/medias/sys_master/public/16653942259742/kazaar-2x.png?impolicy=small&imwidth=284&imdensity=1",
            "https://www.nespresso.com/ecom/medias/sys_master/public/14441096314910/ispirazione-ristretto-italiano-XL.png?impolicy=small&imwidth=284&imdensity=1",
            "https://www.nespresso.com/ecom/medias/sys_master/public/14441096675358/ispirazione-firenze-arpeggio-XL.png?impolicy=small&imwidth=284&imdensity=1",
            "https://www.nespresso.com/ecom/medias/sys_master/public/16653932462110/ispirazione-roma-2x.png?impolicy=small&imwidth=284&imdensity=1"
    );
    private final List<String> productDescriptions = List.of(
            "인디아와 우간다 원두를 오랜 시간 다크 로스팅하여 묵직한 바디감과 함께 기분 좋은 쓴맛의 코코아 향, 그리고 벨벳 같은 부드러운 질감이 특징입니다. 나폴리의 뿌리 깊은 커피 전통과 역사를 반영한 강렬하면서도 아름다운 아로마와 풍미, 질감을 선사 합니다.",
            "진하고 농후한 바디감이 선사하는 와일드한 풍미와 스파이시 향을 느껴보세요. 인디아산 원두의 크리미한 질감과 무게감에 니카라과 커피의 균형잡힌 맛이 부드러움을 줍니다.",
            "미디엄 다크로스팅한 브라질산 원두가 강한 로스트향을 주고 짧게 로스팅한 콜롬비아 원두가 산미를 더해 대조적인 쓴맛과 산미의 조화가 인상적인 커피입니다.",
            "짧게 다크로스팅한 브라질산 원두가 강한 로스팅향과 균형감을 주고 다크로스팅한 코스타리카 원두가 쌉싸름한 코코아향에 무게감을 더해주는 커피입니다. 정교한 그라인딩으로 벨벳처럼 부드러워 거부할 수 없는 크리미한 질감을 표현했습니다.",
            "멕시코 원두를 짧게 라이트로스팅하여 섬세한 우디향과 부드러운 산미를 느낄 수 있으며, 브라질산 원두의 곡물향이 더해져 강렬하면서도 부드러운 우아한 조화를 보이는 커피입니다."
    );
    private final List<Double> totalScores = List.of(38.9, 48.3, 42.1, 49.8, 25.5);

    private final CapsuleRepository capsuleRepository;

    public List<Capsule> create5NespressoCapsules(){

        List<Capsule> capsules = new ArrayList<>();

        for(int i = 0; i < capsuleNames.size(); i++){
            capsules.add(createNespressoCapsule(capsuleNames.get(i),
                    roasts.get(i), acidities.get(i), bodies.get(i), aroma.get(i),
                    imageUrls.get(i), productDescriptions.get(i), totalScores.get(i), 10));
        }

        return capsuleRepository.saveAll(capsules);
    }

    public Capsule createNespressoCapsule(String capsuleName, Double roast, Double acidity, Double body,
                                          String aroma, String imageUrl, String description,
                                          Double totalScore, Integer totalReviewer){
        return createCapsule("네스프레소", "nespresso", capsuleName,
                new CoffeeCriteria(roast, acidity, body), aroma, 1,
                imageUrl, description, totalScore, totalReviewer);
    }

    private Capsule createCapsule(String brandKr, String brandEng, String capsuleName, CoffeeCriteria coffeeCriteria,
                                 String aroma, Integer machineType, String imageUrl, String description,
                                 Double totalScore, Integer totalReviewer) {

        return Capsule.builder()
                .brandKr(brandKr)
                .brandEng(brandEng)
                .capsuleName(capsuleName)
                .coffeeCriteria(coffeeCriteria)
                .aroma(aroma)
                .machineType(machineType)
                .imageUrl(imageUrl)
                .productDescription(description)
                .totalScore(totalScore)
                .totalReviewer(totalReviewer)
                .build();
    }

}