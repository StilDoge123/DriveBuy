package com.drivebuy.service

import com.drivebuy.persistance.entity.MessageEntity
import com.drivebuy.repository.ChatRepository
import com.drivebuy.repository.MessageRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import java.util.*

class MessageServiceTest {

    private lateinit var messageRepository: MessageRepository
    private lateinit var chatRepository: ChatRepository
    private lateinit var messageService: MessageService
    
    @BeforeEach
    fun setup() {
        messageRepository = mock()
        chatRepository = mock()
        messageService = MessageService(messageRepository, chatRepository)
    }

    @Test
    fun `getMessagesByChatId should return messages ordered by timestamp`() {
        val chatId = 1L
        val messages = listOf(
            MessageEntity(id = 1L, chatId = chatId, senderId = "user1", content = "Hello"),
            MessageEntity(id = 2L, chatId = chatId, senderId = "user2", content = "Hi there")
        )

        whenever(messageRepository.findByChatIdOrderByTimestampAsc(chatId)).thenReturn(messages)

        val result = messageService.getMessagesByChatId(chatId)

        assertEquals(2, result.size)
        assertEquals("Hello", result[0].content)
        assertEquals("Hi there", result[1].content)
        verify(messageRepository).findByChatIdOrderByTimestampAsc(chatId)
    }

    @Test
    fun `getLatestMessageByChatId should return latest message`() {
        val chatId = 1L
        val latestMessage = MessageEntity(id = 5L, chatId = chatId, senderId = "user1", content = "Latest")

        whenever(messageRepository.findTop1ByChatIdOrderByTimestampDesc(chatId)).thenReturn(latestMessage)

        val result = messageService.getLatestMessageByChatId(chatId)

        assertNotNull(result)
        assertEquals("Latest", result?.content)
    }

    @Test
    fun `getLatestMessageByChatId should return null when no messages`() {
        val chatId = 999L

        whenever(messageRepository.findTop1ByChatIdOrderByTimestampDesc(chatId)).thenReturn(null)

        val result = messageService.getLatestMessageByChatId(chatId)

        assertNull(result)
    }

    @Test
    fun `markMessageAsRead should mark message as read`() {
        val messageId = 1L
        val message = MessageEntity(id = messageId, chatId = 1L, senderId = "user1", content = "Test", isRead = false)

        whenever(messageRepository.findById(messageId)).thenReturn(Optional.of(message))
        doAnswer { it.arguments[0] }.whenever(messageRepository).save(any())

        messageService.markMessageAsRead(messageId)

        assertTrue(message.isRead)
        verify(messageRepository).save(message)
    }

    @Test
    fun `markMessageAsRead should throw exception when message not found`() {
        val messageId = 999L

        whenever(messageRepository.findById(messageId)).thenReturn(Optional.empty())

        val exception = assertThrows<RuntimeException> {
            messageService.markMessageAsRead(messageId)
        }

        assertEquals("Message not found with id: $messageId", exception.message)
    }

    @Test
    fun `markMessagesAsRead should mark all unread messages as read`() {
        val chatId = 1L
        val userId = "user123"
        val unreadMessages = listOf(
            MessageEntity(id = 1L, chatId = chatId, senderId = "user2", content = "Test1", isRead = false),
            MessageEntity(id = 2L, chatId = chatId, senderId = "user2", content = "Test2", isRead = false)
        )

        whenever(messageRepository.findUnreadMessagesByChatIdAndUserId(chatId, userId)).thenReturn(unreadMessages)
        whenever(messageRepository.saveAll(any<List<MessageEntity>>())).thenReturn(unreadMessages)

        messageService.markMessagesAsRead(chatId, userId)

        unreadMessages.forEach { assertTrue(it.isRead) }
        verify(messageRepository).saveAll(unreadMessages)
    }

    @Test
    fun `getUnreadMessagesByChatId should return unread messages`() {
        val chatId = 1L
        val userId = "user123"
        val unreadMessages = listOf(
            MessageEntity(id = 1L, chatId = chatId, senderId = "user2", content = "Unread1", isRead = false),
            MessageEntity(id = 2L, chatId = chatId, senderId = "user2", content = "Unread2", isRead = false)
        )

        whenever(messageRepository.findUnreadMessagesByChatIdAndUserId(chatId, userId)).thenReturn(unreadMessages)

        val result = messageService.getUnreadMessagesByChatId(chatId, userId)

        assertEquals(2, result.size)
        assertFalse(result[0].isRead)
        assertFalse(result[1].isRead)
    }

    @Test
    fun `countUnreadMessagesByChatId should return unread count`() {
        val chatId = 1L
        val userId = "user123"

        whenever(messageRepository.countUnreadMessagesByChatIdAndUserId(chatId, userId)).thenReturn(5L)

        val result = messageService.countUnreadMessagesByChatId(chatId, userId)

        assertEquals(5L, result)
    }

    @Test
    fun `saveMessage should save and return message`() {
        val message = MessageEntity(id = 0L, chatId = 1L, senderId = "user1", content = "New message")
        val savedMessage = message.copy(id = 10L)

        whenever(messageRepository.save(message)).thenReturn(savedMessage)

        val result = messageService.saveMessage(message)

        assertEquals(10L, result.id)
        assertEquals("New message", result.content)
        verify(messageRepository).save(message)
    }

    @Test
    fun `countTotalUnreadMessagesByUserId should return total unread count`() {
        val userId = "user123"

        whenever(messageRepository.countTotalUnreadMessagesByUserId(userId)).thenReturn(12L)

        val result = messageService.countTotalUnreadMessagesByUserId(userId)

        assertEquals(12L, result)
    }
}
