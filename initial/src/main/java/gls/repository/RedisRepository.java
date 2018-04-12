package gls.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import gls.entity.Coffee;
import gls.entity.User;

@Component
public class RedisRepository {
	@Autowired
	private RedisConnectionFactory factory;

	private <T> RedisOperations<String, T> redisOperations(Class<T> clazz) {
		RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		
		Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(clazz); 
		ObjectMapper om = new ObjectMapper();  
    	om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);  
    	om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);  
    	jackson2JsonRedisSerializer.setObjectMapper(om);  

		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		return redisTemplate;
	}

	@Bean
	public RedisOperations<String, Coffee> coffeeRespository() {
		return this.redisOperations(Coffee.class);
	}

	@Bean
	public RedisOperations<String, User> userRespository() {
		return this.redisOperations(User.class);
	}
}