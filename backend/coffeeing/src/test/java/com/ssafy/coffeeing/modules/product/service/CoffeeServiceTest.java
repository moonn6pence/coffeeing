package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.dummy.CoffeeTestDummy;
import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.product.domain.Coffee;
import com.ssafy.coffeeing.modules.product.domain.CoffeeBookmark;
import com.ssafy.coffeeing.modules.product.dto.CoffeeResponse;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CoffeeBookmarkRepository;
import com.ssafy.coffeeing.modules.product.repository.CoffeeRepository;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

class CoffeeServiceTest extends ServiceTest {

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeBookmarkRepository coffeeBookmarkRepository;

    @MockBean
    private SecurityContextUtils securityContextUtils;

    private Coffee coffee;

    @BeforeEach
    void setUpCoffee() {
        coffee = coffeeRepository.save(CoffeeTestDummy.createMockCoffeeKenyaAA());
    }

    @Test
    @DisplayName("원두 아이디를 통해 캡슐 상세 정보를 조회한다.")
    void Given_ValidCoffeeId_When_GetDetails_Then_Success() {

        // given
        CoffeeBookmark bookmark = CoffeeBookmark.builder()
                .coffee(coffee)
                .member(generalMember)
                .build();
        coffeeBookmarkRepository.save(bookmark);

        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(generalMember);
        CoffeeResponse expected = ProductMapper.supplyCoffeeResponseOf(coffee, true, null);

        // when
        CoffeeResponse actual = coffeeService.getDetail(coffee.getId());

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("존재하지 않는 원두 아이디를 통해 조회할 시 예외를 던진다.")
    void Given_NotFoundCoffeeId_When_GetDetails_Then_ThrowException() {

        // given
        Long invalidId = coffee.getId();
        coffeeRepository.delete(coffee);

        // when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_PRODUCT,
                assertThrows(BusinessException.class, () -> coffeeService.getDetail(invalidId)).getInfo());
    }

    @Test
    @DisplayName("원두 아이디를 통해 원두를 찜한다.")
    void Given_CoffeeId_When_BookmarkingByToggleBookmark_Then_Success() {

        // given
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when
        ToggleResponse actual = coffeeService.toggleBookmark(coffee.getId());

        // then
        assertTrue(actual.result());
        assertNotNull(coffeeBookmarkRepository.findByCoffeeAndMember(coffee,generalMember));
    }

    @Test
    @DisplayName("원두 아이디를 통해 원두를 찜 해제한다.")
    void Given_CoffeeId_When_CancellingBookmarkByToggleBookmark_Then_Success() {

        // given
        CoffeeBookmark bookmark = CoffeeBookmark.builder()
                .coffee(coffee)
                .member(generalMember)
                .build();
        coffeeBookmarkRepository.save(bookmark);

        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when
        ToggleResponse actual = coffeeService.toggleBookmark(coffee.getId());

        // then
        assertFalse(actual.result());
        assertNull(coffeeBookmarkRepository.findByCoffeeAndMember(coffee,generalMember));
    }
}
