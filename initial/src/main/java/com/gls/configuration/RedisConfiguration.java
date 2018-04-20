package com.gls.configuration;



import java.lang.reflect.Method;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfiguration {   
	
	@Value("${spring.redis.host}")
	String host;
	@Value("${spring.redis.port}")
	Integer port;
	@Value("${spring.redis.password}")
	String password;
	@Value("${spring.redis.database}")
	Integer database;
	
    @Bean
    @Primary
    public RedisConnectionFactory lettuceConnectionFactory() {

      LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//        .useSsl()
//        .and()
        .commandTimeout(Duration.ofSeconds(2))
        .shutdownTimeout(Duration.ZERO)
        .build();
      RedisStandaloneConfiguration cfg = new RedisStandaloneConfiguration(host, 6379);
      cfg.setPassword(RedisPassword.of(password));
      cfg.setDatabase(database);
      return new LettuceConnectionFactory(cfg, clientConfig);
    }
    
    @Bean
	@Qualifier
	public KeyGenerator wiselyKeyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				// sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
		Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class); 
    	ObjectMapper om = new ObjectMapper();  
    	om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);  
    	om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);  
    	jackson2JsonRedisSerializer.setObjectMapper(om);  
    	
		RedisSerializationContext.SerializationPair<?> pair = RedisSerializationContext.SerializationPair
				.fromSerializer(jackson2JsonRedisSerializer);
		RedisCacheConfiguration cacheCfg = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair)
				.entryTtl(Duration.ofSeconds(600));
		// return RedisCacheManager.create(connectionFactory);
		return new RedisCacheManager(redisCacheWriter, cacheCfg);
	}
}
