package com.ssafy.coffeeing.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

	private final String REDIS_HOST;
	private final int REDIS_PORT;

	private final String REDIS_PASSWORD;

	public RedisConfig(@Value("${spring.redis.host}") final String host,
					   @Value("${spring.redis.port}") final int port,
					   @Value("${spring.redis.password}") final String password) {
		this.REDIS_HOST = host;
		this.REDIS_PORT = port;
		this.REDIS_PASSWORD = password;
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(REDIS_HOST);
		redisStandaloneConfiguration.setPort(REDIS_PORT);
		redisStandaloneConfiguration.setPassword(REDIS_PASSWORD);
		return new LettuceConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	public RedisTemplate<String, ?> redisTemplate() {
		RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		return redisTemplate;
	}

}
