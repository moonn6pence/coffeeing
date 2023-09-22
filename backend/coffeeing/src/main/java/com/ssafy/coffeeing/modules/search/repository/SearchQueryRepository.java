package com.ssafy.coffeeing.modules.search.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.coffeeing.modules.search.domain.Acidity;
import com.ssafy.coffeeing.modules.search.domain.Body;
import com.ssafy.coffeeing.modules.search.domain.Roast;
import com.ssafy.coffeeing.modules.search.dto.ProductSearchElement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.coffeeing.modules.global.embedded.QCoffeeCriteria.coffeeCriteria;
import static com.ssafy.coffeeing.modules.product.domain.QCapsule.capsule;
import static com.ssafy.coffeeing.modules.product.domain.QCoffee.coffee;

@Repository
@RequiredArgsConstructor
public class SearchQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;


    public Page<ProductSearchElement> searchByBeanConditions(
            List<Roast> roasts,
            List<Acidity> acidities,
            List<Body> bodies,
            List<String> flavorNotes,
            Pageable pageable) {

        List<ProductSearchElement> coffees = jpaQueryFactory
                .select(Projections.fields(ProductSearchElement.class,
                        coffee.id.as("id"),
                        coffee.coffeeNameKr.as("name"),
                        coffee.imageUrl.as("imageUrl")))
                .from(coffee)
                .where(makeConditionsOfRoastWithAcidityWithBody(roasts, acidities, bodies)
                        .or(containsCoffeeFlavorNotes(flavorNotes)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory
                .select(coffee.count())
                .from(coffee)
                .where(makeConditionsOfRoastWithAcidityWithBody(roasts, acidities, bodies)
                        .or(containsCoffeeFlavorNotes(flavorNotes)))
                .fetchOne();

        return new PageImpl<>(coffees, pageable, count);
    }

    public Page<ProductSearchElement> searchByCapsuleConditions(
            List<Roast> roasts,
            List<Acidity> acidities,
            List<Body> bodies,
            List<String> flavorNotes,
            Pageable pageable) {

        List<ProductSearchElement> capsules = jpaQueryFactory
                .select(Projections.fields(ProductSearchElement.class,
                        capsule.id.as("id"),
                        capsule.capsuleNameKr.as("name"),
                        capsule.imageUrl.as("imageUrl")))
                .from(capsule)
                .where(makeConditionsOfRoastWithAcidityWithBody(roasts, acidities, bodies)
                        .or(containsCapsuleFlavorNotes(flavorNotes)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory
                .select(capsule.count())
                .from(capsule)
                .where(makeConditionsOfRoastWithAcidityWithBody(roasts, acidities, bodies)
                        .or(containsCapsuleFlavorNotes(flavorNotes)))
                .fetchOne();

        return new PageImpl<>(capsules, pageable, count);
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
