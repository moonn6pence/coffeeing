package com.ssafy.coffeeing.modules.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.coffeeing.modules.member.domain.Age;
import com.ssafy.coffeeing.modules.member.domain.Gender;
import com.ssafy.coffeeing.modules.member.dto.PreferenceAverage;
import com.ssafy.coffeeing.modules.util.OrderByNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.ssafy.coffeeing.modules.member.domain.QMember.member;
import static com.ssafy.coffeeing.modules.survey.domain.QPreference.preference;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public PreferenceAverage findPreferenceAverageByAgeAndGender(Age age, Gender gender) {

        return jpaQueryFactory
                .select(Projections.fields(PreferenceAverage.class,
                        preference.coffeeCriteria.roast.sum(),
                        preference.coffeeCriteria.acidity.sum(),
                        preference.coffeeCriteria.body.sum()))
                .from(member, preference)
                .where(member.id.eq(preference.memberId))
                .groupBy(member.age, member.gender)
                .having(member.age.eq(age), member.gender.eq(gender))
                .orderBy(OrderByNull.DEFAULT)
                .fetchOne();


    }
}
