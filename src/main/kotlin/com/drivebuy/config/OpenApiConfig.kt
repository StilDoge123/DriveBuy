package com.drivebuy.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("DriveBuy Car Marketplace API")
                .version("1.0")
                .description("API documentation for DriveBuy")
        )
        .addSecurityItem(SecurityRequirement().addList("api_key"))
        .components(
            Components()
                .addSecuritySchemes(
                    "api_key",
                    SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .`in`(SecurityScheme.In.HEADER)
                        .name("X-API-Key")
                )
        )

    @Bean
    fun publicApi(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("public-apis")
        .packagesToScan("com.drivebuy.controller")
        .build()
}