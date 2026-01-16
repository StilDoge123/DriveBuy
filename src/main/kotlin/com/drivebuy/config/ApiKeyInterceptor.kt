package com.drivebuy.config

import com.drivebuy.security.RequiresApiKey
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class ApiKeyInterceptor (
    @Value("\${API_KEY}") private val apiKey: String
) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        if (handler is HandlerMethod) {
            val hasAnnotation = handler.hasMethodAnnotation(RequiresApiKey::class.java) ||
                    handler.beanType.isAnnotationPresent(RequiresApiKey::class.java)

            if (hasAnnotation) {
                val providedKey = request.getHeader("X-API-Key")

                if (providedKey != apiKey) {
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                    response.writer.write("Unauthorized: Invalid or missing API key")
                    return false
                }
            }
        }
        return true
    }
}