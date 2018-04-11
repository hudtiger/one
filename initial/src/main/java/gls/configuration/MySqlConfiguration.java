package gls.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class MySqlConfiguration {
	
	@Bean(name = "glsDataSource")
	@Primary	//指定主连接
	@ConfigurationProperties(prefix = "spring.datasource.gls")
	public DataSource glsDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "glsJdbcTemplate")
    public JdbcTemplate glsJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
