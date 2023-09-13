package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.dummy.CapsuleTestDummy;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.dto.CapsuleResponse;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CapsuleServiceTest extends ServiceTest {

    @Autowired
    private CapsuleService capsuleService;

    @Autowired
    private CapsuleRepository capsuleRepository;

    @Test
    @DisplayName("캡슐 아이디를 통해 캡슐 상세 정보를 조회한다.")
    void Given_ValidCapsuleId_When_GetDetails_Then_Success() {

        // given
        Capsule capsule = CapsuleTestDummy.createMockCapsule1();
        capsuleRepository.save(capsule);
        CapsuleResponse expected = ProductMapper.supplyCapsuleResponseBy(capsule);

        // when
        CapsuleResponse actual = capsuleService.getDetail(capsule.getId());

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("존재하지 않는 캡슐 아이디를 통해 조회할 시 예외를 던진다.")
    void Given_NotFoundCapsuleId_When_GetDetails_Then_ThrowException() {

        // given
        Long invalidId = 6L;

        // when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_PRODUCT,
                assertThrows(BusinessException.class, () -> capsuleService.getDetail(invalidId)).getInfo());
    }
}