package com.ssafy.coffeeing.modules.search.service;

import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.search.domain.*;
import com.ssafy.coffeeing.modules.search.dto.*;
import com.ssafy.coffeeing.modules.search.mapper.SearchMapper;
import com.ssafy.coffeeing.modules.search.repository.SearchQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final CapsuleRepository capsuleRepository;

    private final CoffeeRepository coffeeRepository;

    private final SearchQueryRepository searchQueryRepository;
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
        tags.sort(Comparator.comparing(Tag::name));

        if (tags.size() > AUTO_COMPLETE_SIZE) {
            tags = tags.subList(0, AUTO_COMPLETE_SIZE);
        }
        return SearchMapper.supplyTagsResponseFrom(tags);
    }

    @Transactional(readOnly = true)
    public SearchBeanResponse getProductsBySearchBean(SearchProductRequest searchProductRequest) {
        Page<BeanSearchElement> beanSearchElements;

        beanSearchElements = searchBeanByConditions(searchProductRequest);
        Boolean isLast = searchProductRequest.page() + 1 == beanSearchElements.getTotalPages();

        return SearchMapper.supplySearchBeanResponseOf(
                beanSearchElements.getContent(),
                searchProductRequest.page() + 1,
                isLast,
                beanSearchElements.getTotalPages());
    }

    @Transactional(readOnly = true)
    public SearchCapsuleResponse getProductsBySearchCapsule(SearchProductRequest searchProductRequest) {
        Page<CapsuleSearchElement> capsuleSearchElements;

        capsuleSearchElements = searchCapsuleByConditions(searchProductRequest);
        Boolean isLast = searchProductRequest.page() + 1 == capsuleSearchElements.getTotalPages();

        return SearchMapper.supplySearchCapsuleResponseOf(
                capsuleSearchElements.getContent(),
                searchProductRequest.page() + 1,
                isLast,
                capsuleSearchElements.getTotalPages());
    }

    private Page<BeanSearchElement> searchBeanByConditions(SearchProductRequest searchProductRequest) {
        return searchQueryRepository.searchByBeanConditions(
                searchProductRequest.keyword(),
                roastStringToList(searchProductRequest.roast()),
                acidityStringToList(searchProductRequest.acidity()),
                bodyStringToList(searchProductRequest.body()),
                flavorNoteToList(searchProductRequest.flavorNote()),
                PageRequest.of(searchProductRequest.page(), searchProductRequest.size()));
    }

    private Page<CapsuleSearchElement> searchCapsuleByConditions(SearchProductRequest searchProductRequest) {
        return searchQueryRepository.searchByCapsuleConditions(
                searchProductRequest.keyword(),
                roastStringToList(searchProductRequest.roast()),
                acidityStringToList(searchProductRequest.acidity()),
                bodyStringToList(searchProductRequest.body()),
                flavorNoteToList(searchProductRequest.flavorNote()),
                PageRequest.of(searchProductRequest.page(), searchProductRequest.size()));
    }

    private List<String> flavorNoteToList(String flavorNote) {
        if(Objects.isNull(flavorNote)) return new ArrayList<>();

        return Arrays.stream(flavorNote.split(","))
                .map(String::trim)
                .toList();
    }

    private List<Roast> roastStringToList(String roast) {
        if(Objects.isNull(roast)) return new ArrayList<>();

        return Arrays.stream(roast.split(","))
                .map(String::trim)
                .map(Roast::findRoast)
                .toList();
    }

    private List<Acidity> acidityStringToList(String acidity) {
        if(Objects.isNull(acidity)) return new ArrayList<>();

        return Arrays.stream(acidity.split(","))
                .map(String::trim)
                .map(Acidity::findAcidity)
                .toList();
    }

    private List<Body> bodyStringToList(String body) {
        if(Objects.isNull(body)) return new ArrayList<>();

        return Arrays.stream(body.split(","))
                .map(String::trim)
                .map(Body::findBody)
                .toList();
    }

    private void addCapsulesAndCoffeesToTagElement(
            List<Tag> tags,
            List<Capsule> capsules,
            List<Coffee> coffees) {
        tags.addAll(capsules.stream().map(capsule ->
                new Tag(capsule.getId(), TagType.CAPSULE, capsule.getCapsuleNameKr())).toList());
        tags.addAll(coffees.stream().map(coffee ->
                new Tag(coffee.getId(), TagType.BEAN, coffee.getCoffeeNameKr())).toList());
    }
}
