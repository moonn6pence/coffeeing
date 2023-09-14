package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.dummy.CoffeeTestDummy;
import com.ssafy.coffeeing.dummy.MemberTestDummy;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeBookmark;
import com.ssafy.coffeeing.modules.product.dto.CoffeeResponse;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CoffeeBookmarkRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

public class CoffeeServiceTest extends ServiceTest {

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CoffeeBookmarkRepository coffeeBookmarkRepository;

    @MockBean
    private SecurityContextUtils securityContextUtils;

    @Test
    @DisplayName("원두 아이디를 통해 캡슐 상세 정보를 조회한다.")
    void Given_ValidCoffeeId_When_GetDetails_Then_Success() {

        // given
        Coffee coffee = CoffeeTestDummy.createMockCoffeeWiltonBenitezGeisha();
        Member member = MemberTestDummy.createMemberSean();
        CoffeeBookmark bookmark = CoffeeBookmark.builder()
                .coffee(coffee)
                .member(member)
                .build();

        coffeeRepository.save(coffee);
        memberRepository.save(member);
        coffeeBookmarkRepository.save(bookmark);

        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(member);
        CoffeeResponse expected = ProductMapper.supplyCoffeeResponseBy(coffee, true);

        // when
        CoffeeResponse actual = coffeeService.getDetail(coffee.getId());

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("존재하지 않는 원두 아이디를 통해 조회할 시 예외를 던진다.")
    void Given_NotFoundCoffeeId_When_GetDetails_Then_ThrowException() {

        // given
        Coffee coffee = CoffeeTestDummy.createMockCoffeeKenyaAA();
        coffeeRepository.save(coffee);
        Long invalidId = coffee.getId();
        coffeeRepository.delete(coffee);

        // when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_PRODUCT,
                assertThrows(BusinessException.class, () -> coffeeService.getDetail(invalidId)).getInfo());
    }
}
