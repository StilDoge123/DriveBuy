package com.drivebuy.service

import com.drivebuy.persistance.entity.MessageEntity
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val messagingTemplate: SimpMessagingTemplate
) {

    fun notifyNewMessage(chatId: Long, message: MessageEntity, recipientId: String) {
        val notification = mapOf(
            "type" to "NEW_MESSAGE",
            "chatId" to chatId,
            "message" to mapOf(
                "id" to message.id,
                "content" to message.content,
                "senderId" to message.senderId,
                "timestamp" to message.timestamp.toString(),
                "isRead" to message.isRead
            )
        )
        
        messagingTemplate.convertAndSend("/topic/user/$recipientId", notification)
    }

    fun notifyMessageRead(chatId: Long, messageId: Long, senderId: String) {
        val notification = mapOf(
            "type" to "MESSAGE_READ",
            "chatId" to chatId,
            "messageId" to messageId
        )
        
        messagingTemplate.convertAndSend("/topic/user/$senderId", notification)
    }

    fun notifyUnreadCountUpdate(userId: String, unreadCount: Long) {
        val notification = mapOf(
            "type" to "UNREAD_COUNT_UPDATE",
            "unreadCount" to unreadCount
        )
        
        messagingTemplate.convertAndSend("/topic/user/$userId", notification)
    }
}
