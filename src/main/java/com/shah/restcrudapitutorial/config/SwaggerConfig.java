package com.shah.restcrudapitutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private String name = "Noruklshahlam";
	private String url = "www.google.com";
	private String email = "norulshahlam@gmail.com";
	
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo())
                .select()                 
                .apis(RequestHandlerSelectors.basePackage("com.shah.restcrudapitutorial"))
                .paths(regex("/crud-api.*"))
                .build()
                .useDefaultResponseMessages(false)
                .globalOperationParameters(operationParameters());

    }
    
    private ApiInfo apiInfo() {
    	
    	return new ApiInfoBuilder()
    			.description("Hello World")
    			.title("REST API CRUD operations")
    			.contact(contact())
    			.license("License number WWE87ASR4GH")
    			.termsOfServiceUrl("Licensed by your mother")
    			.version("1.0")
    			.build();
    }
    
    private Contact contact() {
  		return new Contact(name, url, email);
      }
    
    private List<Parameter> operationParameters(){
    	
    	List<Parameter> headers = new ArrayList<>();
    	
    	headers.add(new ParameterBuilder()
    			.name("Content-Type")
    			.modelRef(new ModelRef("string"))
    			.parameterType("header")
    			.required(true)
    			.defaultValue("application/json")
    			.build());
    	
    	return headers;
    }
  
}

	