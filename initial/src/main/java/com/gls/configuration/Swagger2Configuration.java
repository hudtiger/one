package com.gls.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration()
@EnableSwagger2()
public class Swagger2Configuration {

	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(requestHandler->this.apply(requestHandler, "com.gls.demo.controller"))
              //  .paths(PathSelectors.regex("test"))
               .paths(PathSelectors.any())
                .build();
    }

	private boolean apply(RequestHandler requestHandler,String ... paths) {
		boolean res = false;
		for(String path : paths) {
			if(RequestHandlerSelectors.basePackage(path).apply(requestHandler)) {
				res = true;
				break;
			}
		}
		return res;
	}
	
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
                .termsOfServiceUrl("http://blog.didispace.com/")
                .version("1.0")
                .build();
    }
}
