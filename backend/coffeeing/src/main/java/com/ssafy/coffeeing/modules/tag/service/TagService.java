package com.ssafy.coffeeing.modules.tag.service;

import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.tag.domain.TagType;
import com.ssafy.coffeeing.modules.tag.dto.SearchTagRequest;
import com.ssafy.coffeeing.modules.tag.domain.Tag;
import com.ssafy.coffeeing.modules.tag.dto.TagsResponse;
import com.ssafy.coffeeing.modules.tag.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {

    private final CapsuleRepository capsuleRepository;

    private final CoffeeRepository coffeeRepository;

    private static final int AUTO_COMPLETE_SIZE = 10;

    @Transactional(readOnly = true)
    public TagsResponse getProductsBySuggestion(SearchTagRequest searchTagRequest) {
        List<Tag> tags = new ArrayList<>();
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
            List<Tag> tags,
            List<Capsule> capsules,
            List<Coffee> coffees) {
        tags.addAll(capsules.stream().map(capsule ->
                new Tag(capsule.getId(), TagType.CAPSULE, capsule.getCapsuleNameKr()))
                .toList());
        tags.addAll(coffees.stream().map(coffee ->
                        new Tag(coffee.getId(), TagType.BEAN, coffee.getCoffeeNameKr()))
                .toList());
    }
}
