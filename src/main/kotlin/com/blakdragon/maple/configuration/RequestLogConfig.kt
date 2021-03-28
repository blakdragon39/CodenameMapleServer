package com.blakdragon.maple.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter




@Configuration
class RequestLogConfig {
    @Bean
    fun logFilter(): CommonsRequestLoggingFilter? {
        val filter = CommonsRequestLoggingFilter()
        filter.setIncludeQueryString(true)
        filter.setIncludePayload(true)
        filter.setIncludeHeaders(false)
        filter.setAfterMessagePrefix("REQUEST DATA : ")
        return filter
    }
}
