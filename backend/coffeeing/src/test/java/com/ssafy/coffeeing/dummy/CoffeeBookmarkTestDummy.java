package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeBookmark;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Profile("test")
public class CoffeeBookmarkTestDummy {
    public static CoffeeBookmark createMockCoffeeBookmark(Coffee coffee, Member member) {
        return CoffeeBookmark.builder().coffee(coffee).member(member).build();
    }

    public static List<CoffeeBookmark> createMockCoffeeBookmarkList(List<Coffee> coffees, Member member) {
        List<CoffeeBookmark> coffeeBookmarks = new ArrayList<>();
        for (Coffee coffee : coffees) {
            coffeeBookmarks.add(createMockCoffeeBookmark(coffee, member));
        }
        return coffeeBookmarks;
    }
}
