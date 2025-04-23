package com.drivebuy.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "DriveBuy Car Marketplace API",
        version = "1.0",
        description = "API documentation for DriveBuy"
    )
)
class OpenApiConfig {

    @Bean
    fun publicApi(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("public-apis")
        .packagesToScan("com.drivebuy.controller")
        .build()
}