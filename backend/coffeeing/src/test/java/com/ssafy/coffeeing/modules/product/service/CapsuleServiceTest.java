package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.dummy.CapsuleTestDummy;
import com.ssafy.coffeeing.dummy.MemberTestDummy;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleBookmark;
import com.ssafy.coffeeing.modules.product.dto.CapsuleResponse;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CapsuleBookmarkRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class CapsuleServiceTest extends ServiceTest {

    @Autowired
    private CapsuleService capsuleService;

    @Autowired
    private CapsuleRepository capsuleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CapsuleBookmarkRepository capsuleBookmarkRepository;

    @MockBean
    private SecurityContextUtils securityContextUtils;

    @Test
    @DisplayName("캡슐 아이디를 통해 캡슐 상세 정보를 조회한다.")
    void Given_ValidCapsuleId_When_GetDetails_Then_Success() {

        // given
        Capsule capsule = CapsuleTestDummy.createMockCapsule1();
        Member member = MemberTestDummy.createMemberSean();
        CapsuleBookmark bookmark = CapsuleBookmark.builder()
                .capsule(capsule)
                .member(member)
                .build();

        capsuleRepository.save(capsule);
        memberRepository.save(member);
        capsuleBookmarkRepository.save(bookmark);

        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(member);
        CapsuleResponse expected = ProductMapper.supplyCapsuleResponseBy(capsule, true);

        // when
        CapsuleResponse actual = capsuleService.getDetail(capsule.getId());

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("존재하지 않는 캡슐 아이디를 통해 조회할 시 예외를 던진다.")
    void Given_NotFoundCapsuleId_When_GetDetails_Then_ThrowException() {

        // given
        Capsule capsule = CapsuleTestDummy.createMockCapsule1();
        capsuleRepository.save(capsule);
        Long invalidId = capsule.getId();
        capsuleRepository.delete(capsule);

        // when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_PRODUCT,
                assertThrows(BusinessException.class, () -> capsuleService.getDetail(invalidId)).getInfo());
    }
}