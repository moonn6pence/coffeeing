package com.ssafy.coffeeing.modules.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ssafy.coffeeing.modules.auth.domain.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
	Optional<RefreshToken> findByToken(String token);
}
