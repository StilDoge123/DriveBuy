package com.drivebuy.controller

import com.drivebuy.persistance.entity.MessageEntity
import com.drivebuy.service.ChatService
import com.drivebuy.util.AuthUtil
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller

@Controller
class WebSocketController(
    private val chatService: ChatService
) {

    @MessageMapping("/chat.join")
    fun joinChat(
        @Payload data: Map<String, Any>,
        headerAccessor: SimpMessageHeaderAccessor
    ) {
        try {
            val userId = AuthUtil.getUidFromHeaderAccessor(headerAccessor)
            val chatId = (data["chatId"] as Number).toLong()
            
            chatService.getChatById(chatId, userId)
            headerAccessor.sessionAttributes?.put("userId", userId)
            headerAccessor.sessionAttributes?.put("chatId", chatId)
            println("User $userId joined chat $chatId")
        } catch (e: Exception) {
            println("WebSocket join failed: ${e.message}")
        }
    }

    @MessageMapping("/chat.leave")
    fun leaveChat(headerAccessor: SimpMessageHeaderAccessor) {
        try {
            val userId = AuthUtil.getUidFromHeaderAccessor(headerAccessor)
            headerAccessor.sessionAttributes?.remove("chatId")
            println("User $userId left chat")
        } catch (e: Exception) {
            println("WebSocket leave failed: ${e.message}")
        }
    }

    @MessageMapping("/chat.send")
    fun sendMessage(
        @Payload data: Map<String, Any>,
        headerAccessor: SimpMessageHeaderAccessor
    ): MessageEntity? {
        try {
            val senderId = AuthUtil.getUidFromHeaderAccessor(headerAccessor)
            val chatId = (data["chatId"] as Number).toLong()
            val content = data["content"] as String
            
            if (content.isBlank()) {
                println("Empty message content, ignoring")
                return null
            }
            
            val message = chatService.sendMessage(chatId, senderId, content)
            println("Message sent via WebSocket: ${message.id} in chat $chatId")
            return message
            
        } catch (e: Exception) {
            println("WebSocket send message failed: ${e.message}")
            return null
        }
    }

    @MessageMapping("/chat.markRead")
    fun markMessagesAsRead(
        @Payload data: Map<String, Any>,
        headerAccessor: SimpMessageHeaderAccessor
    ) {
        try {
            val userId = AuthUtil.getUidFromHeaderAccessor(headerAccessor)
            val chatId = (data["chatId"] as Number).toLong()
            
            chatService.markMessagesAsRead(chatId, userId)
            println("Messages marked as read in chat $chatId by user $userId")
            
        } catch (e: Exception) {
            println("WebSocket mark read failed: ${e.message}")
        }
    }
}
