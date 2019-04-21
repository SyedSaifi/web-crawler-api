package com.example.webcrawlerapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        List<SecurityScheme> schemeList = new ArrayList<>();
        schemeList.add(new BasicAuth("basicAuth"));

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(metaData())
                .securitySchemes(schemeList);
    }

    private ApiInfo metaData() {

        Contact contact = new Contact("Syed Talibuddin Saifi", "",
                "letters.syed@gmail.com");

        return new ApiInfo(
                "Web Crawler API",
                "API provides capabilities for web page crawling.",
                "1.0",
                "",
                contact,
                "Default license",
                "https://github.com/SyedSaifi/web-crawler-api",
                new ArrayList<>());
    }
}
