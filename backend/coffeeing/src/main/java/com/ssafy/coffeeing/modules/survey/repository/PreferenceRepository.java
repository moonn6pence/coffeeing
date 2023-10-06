package com.ssafy.coffeeing.modules.survey.repository;

import com.ssafy.coffeeing.modules.survey.domain.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    Preference findByMemberId(Long id);
}
