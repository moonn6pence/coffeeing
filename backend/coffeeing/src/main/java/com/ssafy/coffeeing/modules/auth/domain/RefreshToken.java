package com.ssafy.coffeeing.modules.auth.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 1209600000)
public class RefreshToken {
	@Id
	private String email;

	@Indexed
	private String token;

	public RefreshToken(final String email, final String token) {
		this.email = email;
		this.token = token;
	}
}
