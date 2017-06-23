package com.filip.smartlifesaver.config;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final String applicationVersion;

    public SwaggerConfig(@Value("${spring.application.version}") String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    @Bean
    public Docket waecmApiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfo(
                        "SmartLifesaver Backend",
                        "Interactive API documentation for the SmartLifesaver Backend",
                        applicationVersion,
                        null,
                        (Contact) null,
                        null,
                        null
                ))
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .protocols(Sets.newHashSet("http","https"))
                .consumes(new HashSet<>(Collections.singletonList(MediaType.APPLICATION_JSON_VALUE)))
                .produces(new HashSet<>(Collections.singletonList(MediaType.APPLICATION_JSON_VALUE)));
    }

}
