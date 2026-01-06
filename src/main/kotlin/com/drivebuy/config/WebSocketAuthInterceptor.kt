package com.drivebuy.config

import com.drivebuy.util.AuthUtil
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.stereotype.Component

@Component
class WebSocketAuthInterceptor : ChannelInterceptor {

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)
        
        if (accessor != null) {
            when (accessor.command) {
                StompCommand.CONNECT -> {
                    try {
                        val authHeader = accessor.getFirstNativeHeader("Authorization")
                        if (authHeader != null && authHeader.startsWith("Bearer ")) {
                            val token = authHeader.substring(7)
                            val userId = AuthUtil.getUidFromToken(token)
                            accessor.user = object : java.security.Principal {
                                override fun getName(): String = userId
                            }
                        } else {
                            throw RuntimeException("Missing or invalid Authorization header")
                        }
                    } catch (e: Exception) {
                        println("WebSocket authentication failed: ${e.message}")
                        return null
                    }
                }
                else -> {
                    if (accessor.user == null) {
                        println("WebSocket command ${accessor.command} rejected: user not authenticated")
                        return null
                    }
                }
            }
        }
        
        return message
    }
}
