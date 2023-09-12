package com.ssafy.coffeeing.modules.auth.util;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.AuthErrorInfo;
import com.ssafy.coffeeing.modules.global.security.CustomMemberDetail;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTUtils {

	private final Key SIGNING_KEY;
	private final String ACCESS_TOKEN_EMAIL_CLAIM_KEY;
	private final long ACCESS_TOKEN_DURATION_TIME;
	private final long REFRESH_TOKEN_DURATION_TIME;

	public JWTUtils(
		@Value("${jwt.secret}") final String secretKey,
		@Value("${jwt.access-token-claim-key}") final String accessTokenClaimKey,
		@Value("${jwt.access-token-duration}") final long accessTokenDuration,
		@Value("${jwt.refresh-token-duration}") final long refreshTokenDuration) {

		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.SIGNING_KEY = Keys.hmacShaKeyFor(keyBytes);
		this.ACCESS_TOKEN_EMAIL_CLAIM_KEY = accessTokenClaimKey;
		this.ACCESS_TOKEN_DURATION_TIME = accessTokenDuration;
		this.REFRESH_TOKEN_DURATION_TIME = refreshTokenDuration;
	}

	public String generateAccessToken(Authentication authentication) {
		String email = authentication.getName();
		return getJwtBuilder(ACCESS_TOKEN_DURATION_TIME)
			.claim(ACCESS_TOKEN_EMAIL_CLAIM_KEY, email)
			.compact();
	}

	public String generateRefreshToken() {
		return getJwtBuilder(REFRESH_TOKEN_DURATION_TIME).compact();
	}

	private JwtBuilder getJwtBuilder(long tokenDuration) {
		Date issuedTime = new Date();
		Date expiredTime = new Date(issuedTime.getTime() + tokenDuration);

		return Jwts.builder()
			.setIssuedAt(issuedTime)
			.setExpiration(expiredTime)
			.signWith(SIGNING_KEY, SignatureAlgorithm.HS256);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			return false;
		} catch (Exception e) {
			log.warn("올바르지 못한 토큰입니다.");
			log.warn("사용자 Access 토큰값 : {}", token, e);
			throw new BusinessException(AuthErrorInfo.NOT_VALID_TOKEN);
		}
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(SIGNING_KEY)
			.build()
			.parseClaimsJws(accessToken)
			.getBody();

		String email = (String) claims.get(ACCESS_TOKEN_EMAIL_CLAIM_KEY);
		CustomMemberDetail principal = new CustomMemberDetail(email, "");
		return new UsernamePasswordAuthenticationToken(principal, "", AuthorityUtils.NO_AUTHORITIES);
	}
}
