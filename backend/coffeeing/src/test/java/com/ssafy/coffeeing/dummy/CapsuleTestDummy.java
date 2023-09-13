package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.global.embedded.CoffeeCriteria;
import com.ssafy.coffeeing.modules.product.domain.Capsule;

public class CapsuleTestDummy {

    private static Capsule createCapsule(String brandKr, String brandEng, String capsuleName, CoffeeCriteria coffeeCriteria,
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
                .description(description)
                .totalScore(totalScore)
                .totalReviewer(totalReviewer)
                .build();
    }

    public static Capsule createMockCapsule1() {

        return Capsule.builder()
                .brandKr("네스프레소")
                .brandEng("nespresso")
                .capsuleName("로마")
                .coffeeCriteria(new CoffeeCriteria(1.0, 1.0, 1.0))
                .aroma("nutty")
                .machineType(1)
                .imageUrl("https://www.nespresso.com/ecom/medias/sys_master/public/16653932462110/ispirazione-roma-2x.png?impolicy=small&imwidth=284&imdensity=1")
                .description("멕시코 원두를 짧게 라이트로스팅하여 섬세한 우디향과 부드러운 산미를 느낄 수 있으며, 브라질산 원두의 곡물향이 더해져 강렬하면서도 부드러운 우아한 조화를 보이는 커피입니다.")
                .totalScore(38.0)
                .totalReviewer(10)
                .build();
    }
    public static Capsule createMockCapsule2() {

        return Capsule.builder()
                .brandKr("네스프레소")
                .brandEng("nespresso")
                .capsuleName("나폴리")
                .coffeeCriteria(new CoffeeCriteria(1.0, 1.0, 1.0))
                .aroma("chocolate, floral")
                .machineType(1)
                .imageUrl("https://www.nespresso.com/ecom/medias/sys_master/public/16987597701150/ispirazione-napoli-2x.png?impolicy=small&imwidth=284&imdensity=1")
                .description("인디아와 우간다 원두를 오랜 시간 다크 로스팅하여 묵직한 바디감과 함께 기분 좋은 쓴맛의 코코아 향, 그리고 벨벳 같은 부드러운 질감이 특징입니다. 나폴리의 뿌리 깊은 커피 전통과 역사를 반영한 강렬하면서도 아름다운 아로마와 풍미, 질감을 선사 합니다.")
                .totalScore(99.9)
                .totalReviewer(20)
                .build();
    }
}
