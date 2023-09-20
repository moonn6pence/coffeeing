package com.ssafy.coffeeing.modules.search.service;

import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.search.domain.Tag;
import com.ssafy.coffeeing.modules.search.domain.TagType;
import com.ssafy.coffeeing.modules.search.dto.SearchTagRequest;
import com.ssafy.coffeeing.modules.search.dto.TagsResponse;
import com.ssafy.coffeeing.modules.search.mapper.SearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final CapsuleRepository capsuleRepository;

    private final CoffeeRepository coffeeRepository;

    private static final int AUTO_COMPLETE_SIZE = 10;

    @Transactional(readOnly = true)
    public TagsResponse getProductsBySuggestion(SearchTagRequest searchTagRequest) {
        List<Tag> tags = new ArrayList<>();
        String keyword = searchTagRequest.keyword();
        List<Capsule> capsules = capsuleRepository
                .findCapsulesByCapsuleNameKrContainingIgnoreCase(keyword, PageRequest.of(0, AUTO_COMPLETE_SIZE));
        List<Coffee> coffees = coffeeRepository
                .findCoffeesByCoffeeNameKrContainingIgnoreCase(keyword, PageRequest.of(0, AUTO_COMPLETE_SIZE));

        addCapsulesAndCoffeesToTagElement(tags, capsules, coffees);

        Collections.shuffle(tags);

        if (tags.size() > AUTO_COMPLETE_SIZE) {
            tags = tags.subList(0, AUTO_COMPLETE_SIZE);
        }
        return SearchMapper.supplyTagsResponseFrom(tags);
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
