package com.sergioseks.asao.ms.user;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("users")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.regex("/users.*"))
                .build();
    }
     
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring REST Microservice")
                .description("Spring REST Microservice for ASAO users")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/spring-guides/gs-rest-service/blob/master/LICENSE.code.txt")
                .version("2.0")
                .build();
    }
}