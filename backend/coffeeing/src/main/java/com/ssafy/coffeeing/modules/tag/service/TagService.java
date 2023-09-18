package com.ssafy.coffeeing.modules.tag.service;

import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.tag.domain.TagType;
import com.ssafy.coffeeing.modules.tag.dto.SearchTagRequest;
import com.ssafy.coffeeing.modules.tag.dto.TagElement;
import com.ssafy.coffeeing.modules.tag.dto.TagsResponse;
import com.ssafy.coffeeing.modules.tag.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {

    private final CapsuleRepository capsuleRepository;

    private final CoffeeRepository coffeeRepository;

    private static final int AUTO_COMPLETE_SIZE = 10;

    public TagsResponse getProductsBySuggestion(SearchTagRequest searchTagRequest) {
        List<TagElement> tags = new ArrayList<>();
        String keyword = searchTagRequest.keyword();
        List<Capsule> capsules = capsuleRepository
                .findCapsulesByCapsuleNameContainingIgnoreCase(keyword, PageRequest.of(0, AUTO_COMPLETE_SIZE));
        List<Coffee> coffees = coffeeRepository
                .findCoffeesByCoffeeNameContainingIgnoreCase(keyword, PageRequest.of(0, AUTO_COMPLETE_SIZE));

        addCapsulesAndCoffeesToTagElement(tags, capsules, coffees);

        Collections.shuffle(tags);

        if (tags.size() > AUTO_COMPLETE_SIZE) {
            tags = tags.subList(0, AUTO_COMPLETE_SIZE);
        }
        return TagMapper.supplyTagsResponseFrom(tags);
    }

    private void addCapsulesAndCoffeesToTagElement(
            List<TagElement> tags,
            List<Capsule> capsules,
            List<Coffee> coffees) {
        tags.addAll(capsules.stream().map(capsule ->
                new TagElement(capsule.getId(), TagType.CAPSULE, capsule.getCapsuleName()))
                .toList());
        tags.addAll(coffees.stream().map(coffee ->
                        new TagElement(coffee.getId(), TagType.BEAN, coffee.getCoffeeName()))
                .toList());
    }
}
