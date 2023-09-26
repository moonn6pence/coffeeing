package com.ssafy.coffeeing.modules.search.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.coffeeing.modules.search.domain.Acidity;
import com.ssafy.coffeeing.modules.search.domain.Body;
import com.ssafy.coffeeing.modules.search.domain.Roast;
import com.ssafy.coffeeing.modules.search.dto.BeanSearchElement;
import com.ssafy.coffeeing.modules.search.dto.CapsuleSearchElement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.ssafy.coffeeing.modules.global.embedded.QCoffeeCriteria.coffeeCriteria;
import static com.ssafy.coffeeing.modules.product.domain.QCapsule.capsule;
import static com.ssafy.coffeeing.modules.product.domain.QCoffee.coffee;

@Repository
@RequiredArgsConstructor
public class SearchQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<BeanSearchElement> searchByBeanConditions(
            String keyword,
            List<Roast> roasts,
            List<Acidity> acidities,
            List<Body> bodies,
            List<String> flavorNotes,
            Pageable pageable) {
        List<BeanSearchElement> coffees = jpaQueryFactory
                .select(Projections.fields(BeanSearchElement.class,
                        coffee.id.as("id"),
                        coffee.coffeeNameKr.as("nameKr"),
                        coffee.coffeeNameEng.as("nameEng"),
                        coffee.regionKr.as("regionKr"),
                        coffee.regionEng.as("regionEng"),
                        coffee.imageUrl.as("imageUrl")))
                .from(coffee)
                .where(makeConditionsOfRoastWithAcidityWithBody(roasts, acidities, bodies),
                        containsCoffeeFlavorNotes(flavorNotes)
                                .and(isCoffeeNameContainsKeyword(keyword)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory
                .select(coffee.count())
                .from(coffee)
                .where(makeConditionsOfRoastWithAcidityWithBody(roasts, acidities, bodies),
                        containsCoffeeFlavorNotes(flavorNotes)
                                .and(isCoffeeNameContainsKeyword(keyword)))
                .fetchOne();

        return new PageImpl<>(coffees, pageable, count);
    }

    public Page<CapsuleSearchElement> searchByCapsuleConditions(
            String keyword,
            List<Roast> roasts,
            List<Acidity> acidities,
            List<Body> bodies,
            List<String> flavorNotes,
            Pageable pageable) {
        List<CapsuleSearchElement> capsules = jpaQueryFactory
                .select(Projections.fields(CapsuleSearchElement.class,
                        capsule.id.as("id"),
                        capsule.capsuleNameKr.as("nameKr"),
                        capsule.capsuleNameEng.as("nameEng"),
                        capsule.brandKr.as("brandKr"),
                        capsule.brandEng.as("brandEng"),
                        capsule.imageUrl.as("imageUrl")))
                .from(capsule)
                .where(makeConditionsOfRoastWithAcidityWithBody(roasts, acidities, bodies)
                        .or(containsCapsuleFlavorNotes(flavorNotes))
                        .and(isCapsuleNameContainsKeyword(keyword)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory
                .select(capsule.count())
                .from(capsule)
                .where(makeConditionsOfRoastWithAcidityWithBody(roasts, acidities, bodies)
                        .or(containsCapsuleFlavorNotes(flavorNotes))
                        .and(isCapsuleNameContainsKeyword(keyword)))
                .fetchOne();

        return new PageImpl<>(capsules, pageable, count);
    }

    private BooleanExpression isCapsuleNameContainsKeyword(String keyword) {
        if(Objects.isNull(keyword)) {
            return null;
        }
        return capsule.capsuleNameKr.containsIgnoreCase(keyword)
                .or(capsule.capsuleNameEng.containsIgnoreCase(keyword));
    }

    private BooleanExpression isCoffeeNameContainsKeyword(String keyword) {
        if(Objects.isNull(keyword)) {
            return null;
        }

        return coffee.coffeeNameKr.containsIgnoreCase(keyword)
                .or(coffee.coffeeNameEng.containsIgnoreCase(keyword));
    }

    private BooleanBuilder containsCoffeeFlavorNotes(List<String> flavorNotes) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        flavorNotes.forEach(flavorNote -> booleanBuilder.or(coffee.flavorNote.containsIgnoreCase(flavorNote)));
        return booleanBuilder;
    }

    private BooleanBuilder containsCapsuleFlavorNotes(List<String> flavorNotes) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        flavorNotes.forEach(flavorNote -> booleanBuilder.or(capsule.flavorNote.containsIgnoreCase(flavorNote)));
        return booleanBuilder;
    }

    private BooleanBuilder makeConditionsOfRoastWithAcidityWithBody(
            List<Roast> roasts,
            List<Acidity> acidities,
            List<Body> bodies) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        roasts.forEach(roast -> booleanBuilder.or(coffeeCriteria.roast.gt(roast.getMinValue())
                .and(coffeeCriteria.roast.loe(roast.getMaxValue()))));

        acidities.forEach(acidity -> booleanBuilder.or(coffeeCriteria.acidity.gt(acidity.getMinValue())
                .and(coffeeCriteria.acidity.loe(acidity.getMaxValue()))));

        bodies.forEach(body -> booleanBuilder.or(coffeeCriteria.body.gt(body.getMinValue())
                .and(coffeeCriteria.body.loe(body.getMaxValue()))));
        return booleanBuilder;
    }
}
