package com.drivebuy.config

import com.drivebuy.util.AuthUtil
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeHandler
import java.util.*

class CustomHandshakeHandler : HandshakeHandler {

    override fun doHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
        try {
            // Extract Authorization header from the request
            val authHeader = request.headers.getFirst("Authorization")
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                val token = authHeader.substring(7)
                // Verify the token and extract user ID
                val userId = AuthUtil.getUidFromToken(token)
                attributes["userId"] = userId
                return true
            }
        } catch (e: Exception) {
            // Authentication failed
            response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED)
            return false
        }
        
        // No valid token found
        response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED)
        return false
    }
}
