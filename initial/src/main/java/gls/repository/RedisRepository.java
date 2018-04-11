package gls.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import gls.entity.Coffee;

@Component
public class RedisRepository {
	@Autowired
	private RedisConnectionFactory factory;

	private <T> RedisOperations<String, T> redisOperations(Class<T> clazz) {
		RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(clazz));

		return redisTemplate;
	}

	@Bean
	public RedisOperations<String, Coffee> coffeeRespository() {
		return this.redisOperations(Coffee.class);
	}
}