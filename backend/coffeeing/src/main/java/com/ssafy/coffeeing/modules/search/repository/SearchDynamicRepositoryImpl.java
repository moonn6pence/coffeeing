package com.ssafy.coffeeing.modules.search.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.coffeeing.modules.global.embedded.QCoffeeCriteria;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.search.domain.Acidity;
import com.ssafy.coffeeing.modules.search.domain.Body;
import com.ssafy.coffeeing.modules.search.domain.Roast;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.ssafy.coffeeing.modules.global.embedded.QCoffeeCriteria.*;
import static com.ssafy.coffeeing.modules.product.domain.QCapsule.capsule;
import static com.ssafy.coffeeing.modules.product.domain.QCoffee.coffee;

@Repository
@RequiredArgsConstructor
public class SearchDynamicRepositoryImpl implements SearchDynamicRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<Coffee> searchByBeanConditions(
            List<Roast> roasts,
            List<Acidity> acidities,
            List<Body> bodies,
            String flavorNote,
            Integer page,
            Integer size) {

        List<Coffee> coffees = jpaQueryFactory.selectFrom(coffee)
                .where(makeConditionsOfRoastWithAcidityWithBody(roasts, acidities, bodies))
                .where(coffee.flavorNote.contains(flavorNote))
                .offset(page)
                .limit(size)
                .fetch();

        return null;
    }

    @Override
    public Slice<Capsule> searchByCapsuleConditions(
            List<Roast> roasts,
            List<Acidity> acidities,
            List<Body> bodies,
            String flavorNote,
            Integer page,
            Integer size) {

        List<Capsule> capsules = jpaQueryFactory.selectFrom(capsule)
                .where(makeConditionsOfRoastWithAcidityWithBody(roasts, acidities, bodies))
                .where(capsule.flavorNote.contains(flavorNote))
                .offset(page)
                .limit(size)
                .fetch();

        return new SliceImpl<>(capsules, );
    }

    private BooleanBuilder makeConditionsOfRoastWithAcidityWithBody(
            List<Roast> roasts,
            List<Acidity> acidities,
            List<Body> bodies) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        roasts.forEach(roast -> {
            booleanBuilder.and(coffeeCriteria.roast.gt(roast.getMinValue())
                    .and(coffeeCriteria.roast.loe(roast.getMaxValue())));
        });

        acidities.forEach(acidity -> {
            booleanBuilder.and(coffeeCriteria.acidity.gt(acidity.getMinValue())
                    .and(coffeeCriteria.acidity.loe(acidity.getMaxValue())));
        });

        bodies.forEach(body -> {
            booleanBuilder.and(coffeeCriteria.body.gt(body.getMinValue())
                    .and(coffeeCriteria.body.loe(body.getMaxValue())));
        });

        return booleanBuilder;
    }
}
