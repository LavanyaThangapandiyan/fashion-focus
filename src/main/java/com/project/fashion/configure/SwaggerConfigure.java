package com.project.fashion.configure;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfigure {
	@Bean
	    public Docket api() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.project.fashion"))
	                .paths(PathSelectors.any())
	                .build()
	                .apiInfo(apiInfo())
	                .securitySchemes(Arrays.asList(apiKey()));
	    }
	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("My API Documentation")
	                .description("Welcome to Online Fashion Focus  Boutique")
	                .version("1.0.0")
	                .build();
	    }
	    private ApiKey apiKey() {
	        return new ApiKey("apiKey", "apiKey", "header");
	    }
}
