package com.drivebuy.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() } // Disable CSRF for API endpoints
            .authorizeHttpRequests { requests ->
                requests
                    // Public endpoints
                    .requestMatchers(
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/ads/**",
                        "/brands/**",
                        "/models/**",
                        "/locations/**",
                        "/users/**",
                        "/bodyTypes/**",
                        "/carConditions/**",
                        "/carFeatures/**",
                        "/colors/**",
                        "/cylinderCounts/**",
                        "/doorCounts/**",
                        "/driveTypes/**",
                        "/fuelTypes/**",
                        "/steeringPositions/**",
                        "/transmissionTypes/**"
                    ).permitAll()
                    // All other endpoints require authentication
                    .anyRequest().authenticated()
            }

        return http.build()
    }

    // Optional: Add CORS configuration if needed
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*") // Adjust in production
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}