package com.trimix.backendpersonas.configuration;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Clase de configuracion swagger
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(Predicates.or(PathSelectors.ant("/api/**")))
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "API trimix personas",
                "Aplicación CRUD de personas",
                "1.0",
                "http://https://www.trimix.com.ar/",
                new Contact("Trimix", "http://https://www.trimix.com.ar/", "email-ejemplo@trimix.com"),
                "Licencia",
                "Licencia URL",
                Collections.emptyList()
        );
    }
}
