package com.ssafy.coffeeing.dummy;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleBookmark;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeBookmark;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Profile("test")
public class BookmarkTestDummy {
    public static CoffeeBookmark createMockCoffeeBookmark(Coffee coffee, Member member) {
        return CoffeeBookmark.builder().coffee(coffee).member(member).build();
    }

    public static CapsuleBookmark createMockCapsuleBookmark(Capsule capsule, Member member) {
        return CapsuleBookmark.builder().capsule(capsule).member(member).build();
    }

    public static List<CoffeeBookmark> createMockCoffeeBookmarkList(List<Coffee> coffees, Member member) {
        List<CoffeeBookmark> coffeeBookmarks = new ArrayList<>();
        for (Coffee coffee : coffees) {
            coffeeBookmarks.add(createMockCoffeeBookmark(coffee, member));
        }
        return coffeeBookmarks;
    }

    public static List<CapsuleBookmark> createMockCapsuleBookmarkList(List<Capsule> capsules, Member member){
        List<CapsuleBookmark> capsuleBookmarks = new ArrayList<>();
        for(Capsule capsule: capsules){
            capsuleBookmarks.add(createMockCapsuleBookmark(capsule,member));
        }
        return capsuleBookmarks;
    }

}
