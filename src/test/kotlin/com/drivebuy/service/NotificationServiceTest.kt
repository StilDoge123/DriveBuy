package com.drivebuy.service

import com.drivebuy.persistance.entity.MessageEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.messaging.simp.SimpMessagingTemplate

class NotificationServiceTest {

    private lateinit var messagingTemplate: SimpMessagingTemplate
    private lateinit var notificationService: NotificationService
    
    @BeforeEach
    fun setup() {
        messagingTemplate = mock()
        notificationService = NotificationService(messagingTemplate)
    }

    @Test
    fun `notifyNewMessage should send notification to recipient`() {
        val chatId = 1L
        val message = MessageEntity(
            id = 10L,
            chatId = chatId,
            senderId = "sender123",
            content = "Hello!",
            isRead = false
        )
        val recipientId = "recipient456"

        notificationService.notifyNewMessage(chatId, message, recipientId)

        verify(messagingTemplate).convertAndSend(
            eq("/topic/user/$recipientId"),
            any<Map<String, Any>>()
        )
    }

    @Test
    fun `notifyMessageRead should send read notification to sender`() {
        val chatId = 1L
        val messageId = 10L
        val senderId = "sender123"

        notificationService.notifyMessageRead(chatId, messageId, senderId)

        verify(messagingTemplate).convertAndSend(
            eq("/topic/user/$senderId"),
            any<Map<String, Any>>()
        )
    }

    @Test
    fun `notifyUnreadCountUpdate should send unread count update`() {
        val userId = "user123"
        val unreadCount = 5L

        notificationService.notifyUnreadCountUpdate(userId, unreadCount)

        verify(messagingTemplate).convertAndSend(
            eq("/topic/user/$userId"),
            any<Map<String, Any>>()
        )
    }

    @Test
    fun `notifyUnreadCountUpdate should handle zero unread count`() {
        val userId = "user123"
        val unreadCount = 0L

        notificationService.notifyUnreadCountUpdate(userId, unreadCount)

        verify(messagingTemplate).convertAndSend(
            eq("/topic/user/$userId"),
            any<Map<String, Any>>()
        )
    }
}
