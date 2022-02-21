package com.shah.restcrudapitutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo())
                .select()                 
                .apis(RequestHandlerSelectors.basePackage("com.shah.restcrudapitutorial"))
                .paths(regex("/crud-api.*"))
                .build();

    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo(
        		"CRUD Rest API tutorials",
                "APIs for MyApp.",
                "1.0",
                "Terms of service",
                new Contact("Feedback", "www.org.com", "test@emaildomain.com"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }
}