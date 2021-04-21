package com.blakdragon.maple.configuration

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@Configuration
class WebConfig {

    @Bean
    fun corsFilterRegistration(): FilterRegistrationBean<*> {
        val config = CorsConfiguration().apply {
            applyPermitDefaultValues()
            allowedOrigins = listOf("*")
            allowedHeaders = listOf("*")
            allowedMethods = listOf("*")
            exposedHeaders = listOf("content-length")
        }

        val source = UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }

        return FilterRegistrationBean(CorsFilter(source)).apply {
            order = 0
        }
    }
}
