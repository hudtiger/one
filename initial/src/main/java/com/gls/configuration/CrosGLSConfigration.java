package com.gls.configuration;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import io.swagger.models.HttpMethod;

@Configuration
public class CrosGLSConfigration {
	private CorsConfiguration build() {
		CorsConfiguration cfg = new CorsConfiguration();
		cfg.addAllowedOrigin("*");
		cfg.addAllowedHeader("*");
		cfg.setAllowedMethods(Collections.unmodifiableList(Arrays.asList(HttpMethod.GET.name(),HttpMethod.POST.name())));
		return cfg;
	}
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", build()); 
		return new CorsFilter(source);
	}
}
