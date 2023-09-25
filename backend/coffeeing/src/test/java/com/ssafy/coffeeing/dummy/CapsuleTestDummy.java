package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.global.embedded.CoffeeCriteria;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Profile("test")
public class CapsuleTestDummy {

    private static Capsule createMockCapsule(String brandKr, String brandEng, String capsuleNameKr, String capsuleNameEng, CoffeeCriteria coffeeCriteria,
                                             String flavorNote, Integer machineType, String imageUrl, String description,
                                             Double totalScore, Integer totalReviewer) {

        return Capsule.builder()
                .brandKr(brandKr)
                .brandEng(brandEng)
                .capsuleNameKr(capsuleNameKr)
                .capsuleNameEng(capsuleNameEng)
                .coffeeCriteria(coffeeCriteria)
                .flavorNote(flavorNote)
                .machineType(machineType)
                .imageUrl(imageUrl)
                .productDescription(description)
                .totalScore(totalScore)
                .totalReviewer(totalReviewer)
                .build();
    }

    public static Capsule createMockCapsuleRoma() {

        return Capsule.builder()
                .brandKr("네스프레소")
                .brandEng("nespresso")
                .capsuleNameKr("로마 인빅타")
                .capsuleNameEng("Roma Invicta")
                .coffeeCriteria(new CoffeeCriteria(1.0, 1.0, 1.0))
                .flavorNote("nutty")
                .machineType(1)
                .imageUrl("https://www.nespresso.com/ecom/medias/sys_master/public/16653932462110/ispirazione-roma-2x.png?impolicy=small&imwidth=284&imdensity=1")
                .productDescription("멕시코 원두를 짧게 라이트로스팅하여 섬세한 우디향과 부드러운 산미를 느낄 수 있으며, 브라질산 원두의 곡물향이 더해져 강렬하면서도 부드러운 우아한 조화를 보이는 커피입니다.")
                .totalScore(38.0)
                .totalReviewer(10)
                .build();
    }

    public static Capsule createMockCapsuleNapoli() {

        return Capsule.builder()
                .brandKr("네스프레소")
                .brandEng("nespresso")
                .capsuleNameKr("나폴리")
                .capsuleNameEng("Napoli")
                .coffeeCriteria(new CoffeeCriteria(1.0, 1.0, 1.0))
                .flavorNote("chocolate, floral")
                .machineType(1)
                .imageUrl("https://www.nespresso.com/ecom/medias/sys_master/public/16987597701150/ispirazione-napoli-2x.png?impolicy=small&imwidth=284&imdensity=1")
                .productDescription("인디아와 우간다 원두를 오랜 시간 다크 로스팅하여 묵직한 바디감과 함께 기분 좋은 쓴맛의 코코아 향, 그리고 벨벳 같은 부드러운 질감이 특징입니다. 나폴리의 뿌리 깊은 커피 전통과 역사를 반영한 강렬하면서도 아름다운 아로마와 풍미, 질감을 선사 합니다.")
                .totalScore(99.9)
                .totalReviewer(20)
                .build();
    }

    public static List<Capsule> create25GenericCapsules() {
        List<Capsule> capsules = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            capsules.add(createMockCapsule(
                    "Generic 한국 브랜드" + i,
                    "Generic English brand " + i,
                    "제네릭 캡슐명 " + i,
                    "Generic Capsule Name " + i,
                    new CoffeeCriteria(0.5, 0.5, 0.5),
                    "Generic aroma " + i,
                    1,
                    "Generic Url " + i,
                    "generic description " + i,
                    50.0,
                    12
            ));
        }
        return capsules;
    }



    public static List<Capsule> create10SpecifiedFlavorCapsules(String flavorNote) {
        List<Capsule> capsules = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            capsules.add(createMockCapsule(
                    "Generic 한국 브랜드" + i,
                    "Generic English brand " + i,
                    "제네릭 캡슐명 " + i,
                    "Generic Capsule Name " + i,
                    new CoffeeCriteria(0.5, 0.5, 0.5),
                    flavorNote,
                    1,
                    "Generic Url " + i,
                    "generic description " + i,
                    50.0,
                    12
            ));
        }
        return capsules;
    }

    public static List<Capsule> createSearchResultExpectCapsules() {
        List<Capsule> capsules = new ArrayList<>();
        capsules.add(createMockCapsule(
                "Generic 한국 브랜드",
                "Generic English brand",
                "윌튼 베네티즈 게이샤",
                "Wilton Benitez Geisha",
                new CoffeeCriteria(0.2, 0.25, null),
                "Fruits",
                1,
                "Generic Url ",
                "generic description ",
                50.0,
                12));
        capsules.add(createMockCapsule(
                "Generic 한국 브랜드",
                "Generic English brand",
                "예가체프 멩게샤 네츄럴",
                "Yirgacheffe Mengesha Natural",
                new CoffeeCriteria(0.8, 0.5, 0.6),
                "Fruits",
                1,
                "Generic Url ",
                "generic description ",
                50.0,
                12));
        capsules.add(createMockCapsule(
                "Generic 한국 브랜드",
                "Generic English brand",
                "파나마 게이샤 핑카 데브라 비비드",
                "Panama Geisha Finca Debra Vivid",
                new CoffeeCriteria(0.8, 0.9, 0.9),
                "Chocolate",
                1,
                "Generic Url ",
                "generic description ",
                50.0,
                12));
        capsules.add(createMockCapsule(
                "Generic 한국 브랜드",
                "Generic English brand",
                "파나마 게이샤 핑카 데브라 에코",
                "Panama Geisha Finca Debra Echo",
                new CoffeeCriteria(0.2, 0.75, 0.3),
                "Fruits,Nutty",
                1,
                "Generic Url ",
                "generic description ",
                50.0,
                12));
        return capsules;
    }

    public static Integer expectedSearchCount(int index) {
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(2);
        expected.add(3);
        expected.add(1);
        expected.add(1);
        expected.add(2);
        expected.add(1);
        expected.add(3);
        expected.add(3);
        expected.add(2);
        expected.add(2);
        expected.add(4);
        return expected.get(index);
    }
}
