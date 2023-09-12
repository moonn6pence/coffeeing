package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.dummy.CapsuleDummy;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.dto.CapsuleResponse;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

class CapsuleServiceTest extends ServiceTest {

    @Autowired
    private CapsuleService capsuleService;

    @Autowired
    private CapsuleRepository capsuleRepository;

    @Test
    @DisplayName("캡슐 아이디를 통해 캡슐 상세 정보를 조회한다.")
    @Transactional
    void getDetail() {

        // given
        Capsule expected = CapsuleDummy.createMockCapsule1();
        capsuleRepository.save(expected);

        // when
        CapsuleResponse actual = capsuleService.getDetail(expected.getId());

        // then
        assertEquals(expected, actual);
    }
}