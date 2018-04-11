package gls.configuration;



import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class RedisConfiguration {   
	
	@Value("${redis.url}")
	public String host;
	@Value("${redis.port}")
	public Integer port;
	@Value("${redis.password}")
	public String password;
	
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
      cfg.setDatabase(15);
      return new LettuceConnectionFactory(cfg, clientConfig);
    }
}
