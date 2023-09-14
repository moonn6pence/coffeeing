package com.ssafy.coffeeing.modules.auth.util;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.AuthErrorInfo;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;

class JWTUtilsTest extends ServiceTest {

    @Autowired
    private JWTUtils jwtUtils;

    private final JWTUtils expiredJwtUtils = new JWTUtils("SigningKeyValue13SigningKeyValue13SigningKeyValue13", "email", 0, 0);

    @Test
    @DisplayName("만료 되지 않은 Token 검증 시 True Return")
    void Given_ValidAccessToken_When_Validate_Then_ReturnSuccess() {
        //given
        String accessToken = getAccessToken(jwtUtils);
        String refreshToken = jwtUtils.generateRefreshToken();
        //when, then
        assertAll(
                ()-> assertTrue(jwtUtils.validateToken(accessToken)),
                ()-> assertTrue(jwtUtils.validateToken(refreshToken)),
                ()-> assertEquals(generalMember.getEmail(), jwtUtils.getAuthentication(accessToken).getName())
        );
    }

    @Test
    @DisplayName("만료된 Token 검증 시 False Return")
    void Given_ExpiredAccessToken_When_Validate_Then_ReturnSuccess() {
        //given
        String accessToken = getAccessToken(expiredJwtUtils);
        String refreshToken = expiredJwtUtils.generateRefreshToken();
        //when, then
        assertAll(
                ()->assertFalse(expiredJwtUtils.validateToken(accessToken)),
                ()->assertFalse(expiredJwtUtils.validateToken(refreshToken))
        );
    }

    @Test
    @DisplayName("위변조, 또는 손상된 Token 검증 시 예외 처리")
    void Given_NotValidAccessToken_When_Validate_Then_ThrowNotValidTokenException() {
        //given
        String notValidAccessToken = getAccessToken(jwtUtils).substring(1);
        String notValidRefreshToken = jwtUtils.generateRefreshToken().substring(1);
        //when, then

        assertAll(
                () -> assertEquals(AuthErrorInfo.NOT_VALID_TOKEN,
                        assertThrows(BusinessException.class, ()->jwtUtils.validateToken(notValidAccessToken)).getInfo()),
                () -> assertEquals(AuthErrorInfo.NOT_VALID_TOKEN,
                        assertThrows(BusinessException.class, ()->jwtUtils.validateToken(notValidRefreshToken)).getInfo())
        );

    }

    private String getAccessToken(JWTUtils jwtUtils) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(generalMember.getEmail(), null);
        return jwtUtils.generateAccessToken(authentication);
    }
}