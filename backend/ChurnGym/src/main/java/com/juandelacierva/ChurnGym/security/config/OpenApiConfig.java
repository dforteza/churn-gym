package com.juandelacierva.ChurnGym.security.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.security.*;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {

    private static final String SCHEME_NAME   = "bearerAuth";
    private static final String BEARER_FORMAT = "JWT";

    @Bean
    public OpenApiCustomizer sortParameterCustomizer() {
        return openApi -> openApi.getPaths().forEach((path, pathItem) ->
            pathItem.readOperations().forEach(operation -> {
                if (operation.getParameters() != null) {
                    operation.getParameters().stream()
                        .filter(p -> "sort".equals(p.getName()))
                        .forEach(p -> {
                            p.setSchema(new StringSchema().example("probabilidadAbandono,desc"));
                            p.setDescription("Campo y dirección: probabilidadAbandono,desc — nivelRiesgo,asc");
                        });
                }
            })
        );
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat(BEARER_FORMAT)
                                        .in(SecurityScheme.In.HEADER)
                                        .description("Token JWT obtenido en POST /api/v1/auth/login")
                        )
                )
                .info(new Info()
                        .title("ChurnGym API")
                        .version("2.0.0")
                        .description("API REST para la detección temprana de abandono (churn) en clientes de gimnasios.")
                        .contact(new Contact()
                                .name("Diego Forteza & Javier Escudero")
                                .email("dfortezabenito@gmail.com"))
                );
    }
}