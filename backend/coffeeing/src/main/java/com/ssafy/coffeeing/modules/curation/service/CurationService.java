package com.ssafy.coffeeing.modules.curation.service;

import com.ssafy.coffeeing.modules.curation.dto.CurationElement;
import com.ssafy.coffeeing.modules.curation.dto.CurationResponse;
import com.ssafy.coffeeing.modules.curation.mapper.CurationMapper;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Age;
import com.ssafy.coffeeing.modules.member.domain.Gender;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.recommend.service.RecommendService;
import com.ssafy.coffeeing.modules.util.base.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CurationService {

    private final RecommendService recommendService;
    private final SecurityContextUtils securityContextUtils;
    private final RandomUtil randomUtil;

    @Transactional(readOnly = true)
    public CurationResponse getOpenCuration(Boolean isCapsule) {

        List<CurationElement> curations = new ArrayList<>();

        curations.add(findByPopularity(isCapsule));

        curations.add(findByCharacteristic(isCapsule));

        return CurationMapper.supplyCurationResponseFrom(curations);
    }

    @Transactional(readOnly = true)
    public CurationResponse getCustomCuration(Boolean isCapsule) {

        Member member = securityContextUtils.getCurrnetAuthenticatedMember();

        List<CurationElement> curations = new ArrayList<>();

        curations.add(findByAgeAndGender(isCapsule, member.getAge(), member.getGender()));

        curations.add(findByMemberLikedProduct(isCapsule, member));

        return CurationMapper.supplyCurationResponseFrom(curations);
    }

    private CurationElement findByPopularity(Boolean isCapsule) {



        return null;
    }

    private CurationElement findByCharacteristic(Boolean isCapsule) {

        return null;
    }

    private CurationElement findByAgeAndGender(Boolean isCapsule, Age age, Gender gender) {

        return null;
    }

    private CurationElement findByMemberLikedProduct(Boolean isCapsule, Member member) {

        return null;
    }
}
