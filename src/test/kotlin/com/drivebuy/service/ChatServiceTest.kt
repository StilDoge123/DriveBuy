package com.drivebuy.service

import com.drivebuy.persistance.entity.CarAdEntity
import com.drivebuy.persistance.entity.ChatEntity
import com.drivebuy.persistance.entity.MessageEntity
import com.drivebuy.repository.AdRepository
import com.drivebuy.repository.ChatRepository
import com.drivebuy.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import java.util.*

class ChatServiceTest {

    private lateinit var chatRepository: ChatRepository
    private lateinit var messageService: MessageService
    private lateinit var adRepository: AdRepository
    private lateinit var userRepository: UserRepository
    private lateinit var notificationService: NotificationService
    private lateinit var chatService: ChatService
    
    @BeforeEach
    fun setup() {
        chatRepository = mock()
        messageService = mock()
        adRepository = mock()
        userRepository = mock()
        notificationService = mock()
        
        chatService = ChatService(
            chatRepository,
            messageService,
            adRepository,
            userRepository,
            notificationService
        )
    }

    @Test
    fun `createOrGetChat should create new chat when not exists`() {
        val adId = 1L
        val buyerId = "buyer123"
        val sellerId = "seller456"
        val ad = CarAdEntity(
            id = adId,
            userId = sellerId,
            make = "Toyota",
            model = "Camry",
            title = "Title",
            description = "Desc",
            year = 2020,
            color = "Red",
            bodyType = "Sedan",
            condition = "Used",
            hp = 200,
            displacement = 2500,
            mileage = 50000,
            price = 20000,
            doorCount = "4",
            cylinderCount = "4",
            transmissionType = "Automatic",
            fuelType = "Gasoline",
            steeringPosition = "Left",
            driveType = "FWD",
            ownerCount = 1,
            phone = "123",
            region = "Region",
            city = "City"
        )

        whenever(adRepository.findById(adId)).thenReturn(Optional.of(ad))
        whenever(userRepository.existsById(buyerId)).thenReturn(true)
        whenever(userRepository.existsById(sellerId)).thenReturn(true)
        whenever(chatRepository.findByAdIdAndBuyerIdAndSellerId(adId, buyerId, sellerId)).thenReturn(null)
        doAnswer { it.arguments[0] }.whenever(chatRepository).save(any())

        val result = chatService.createOrGetChat(adId, buyerId, sellerId)

        assertNotNull(result)
        assertEquals(adId, result.adId)
        assertEquals(buyerId, result.buyerId)
        assertEquals(sellerId, result.sellerId)
        verify(chatRepository).save(any())
    }

    @Test
    fun `createOrGetChat should return existing chat when already exists`() {
        val adId = 1L
        val buyerId = "buyer123"
        val sellerId = "seller456"
        val ad = CarAdEntity(
            id = adId,
            userId = sellerId,
            make = "Toyota",
            model = "Camry",
            title = "Title",
            description = "Desc",
            year = 2020,
            color = "Red",
            bodyType = "Sedan",
            condition = "Used",
            hp = 200,
            displacement = 2500,
            mileage = 50000,
            price = 20000,
            doorCount = "4",
            cylinderCount = "4",
            transmissionType = "Automatic",
            fuelType = "Gasoline",
            steeringPosition = "Left",
            driveType = "FWD",
            ownerCount = 1,
            phone = "123",
            region = "Region",
            city = "City"
        )
        val existingChat = ChatEntity(id = 100L, adId = adId, buyerId = buyerId, sellerId = sellerId)

        whenever(adRepository.findById(adId)).thenReturn(Optional.of(ad))
        whenever(userRepository.existsById(buyerId)).thenReturn(true)
        whenever(userRepository.existsById(sellerId)).thenReturn(true)
        whenever(chatRepository.findByAdIdAndBuyerIdAndSellerId(adId, buyerId, sellerId)).thenReturn(existingChat)

        val result = chatService.createOrGetChat(adId, buyerId, sellerId)

        assertEquals(existingChat, result)
        verify(chatRepository, never()).save(any())
    }

    @Test
    fun `createOrGetChat should throw exception when ad not found`() {
        val adId = 999L
        val buyerId = "buyer123"
        val sellerId = "seller456"

        whenever(adRepository.findById(adId)).thenReturn(Optional.empty())

        val exception = assertThrows<RuntimeException> {
            chatService.createOrGetChat(adId, buyerId, sellerId)
        }

        assertEquals("Ad not found with id: $adId", exception.message)
    }

    @Test
    fun `createOrGetChat should throw exception when buyer equals seller`() {
        val adId = 1L
        val userId = "user123"
        val ad = CarAdEntity(
            id = adId,
            userId = userId,
            make = "Toyota",
            model = "Camry",
            title = "Title",
            description = "Desc",
            year = 2020,
            color = "Red",
            bodyType = "Sedan",
            condition = "Used",
            hp = 200,
            displacement = 2500,
            mileage = 50000,
            price = 20000,
            doorCount = "4",
            cylinderCount = "4",
            transmissionType = "Automatic",
            fuelType = "Gasoline",
            steeringPosition = "Left",
            driveType = "FWD",
            ownerCount = 1,
            phone = "123",
            region = "Region",
            city = "City"
        )

        whenever(adRepository.findById(adId)).thenReturn(Optional.of(ad))

        val exception = assertThrows<RuntimeException> {
            chatService.createOrGetChat(adId, userId, userId)
        }

        assertEquals("Cannot create chat with yourself", exception.message)
    }

    @Test
    fun `getUserChats should return user's chats`() {
        val userId = "user123"
        val chats = listOf(
            ChatEntity(id = 1L, adId = 1L, buyerId = userId, sellerId = "seller1"),
            ChatEntity(id = 2L, adId = 2L, buyerId = "buyer1", sellerId = userId)
        )

        whenever(chatRepository.findUserChatsOrderByLastMessage(userId)).thenReturn(chats)

        val result = chatService.getUserChats(userId)

        assertEquals(2, result.size)
        verify(chatRepository).findUserChatsOrderByLastMessage(userId)
    }

    @Test
    fun `getChatById should return chat when user is authorized`() {
        val chatId = 1L
        val userId = "buyer123"
        val chat = ChatEntity(id = chatId, adId = 1L, buyerId = userId, sellerId = "seller456")

        whenever(chatRepository.findById(chatId)).thenReturn(Optional.of(chat))

        val result = chatService.getChatById(chatId, userId)

        assertEquals(chat, result)
    }

    @Test
    fun `getChatById should throw exception when user not authorized`() {
        val chatId = 1L
        val userId = "unauthorized789"
        val chat = ChatEntity(id = chatId, adId = 1L, buyerId = "buyer123", sellerId = "seller456")

        whenever(chatRepository.findById(chatId)).thenReturn(Optional.of(chat))

        val exception = assertThrows<RuntimeException> {
            chatService.getChatById(chatId, userId)
        }

        assertEquals("User $userId is not authorized to access chat $chatId", exception.message)
    }

    @Test
    fun `sendMessage should save message and notify recipient`() {
        val chatId = 1L
        val senderId = "buyer123"
        val recipientId = "seller456"
        val content = "Hello!"
        val chat = ChatEntity(id = chatId, adId = 1L, buyerId = senderId, sellerId = recipientId)
        val message = MessageEntity(id = 10L, chatId = chatId, senderId = senderId, content = content)

        whenever(chatRepository.findById(chatId)).thenReturn(Optional.of(chat))
        whenever(messageService.saveMessage(any())).thenReturn(message)
        doAnswer { it.arguments[0] }.whenever(chatRepository).save(any())
        whenever(messageService.countTotalUnreadMessagesByUserId(recipientId)).thenReturn(5L)

        val result = chatService.sendMessage(chatId, senderId, content)

        assertEquals(message, result)
        verify(messageService).saveMessage(any())
        verify(notificationService).notifyNewMessage(chatId, message, recipientId)
        verify(notificationService).notifyUnreadCountUpdate(recipientId, 5L)
    }

    @Test
    fun `markMessagesAsRead should mark messages as read`() {
        val chatId = 1L
        val userId = "buyer123"
        val sellerId = "seller456"
        val chat = ChatEntity(id = chatId, adId = 1L, buyerId = userId, sellerId = sellerId)

        whenever(chatRepository.findById(chatId)).thenReturn(Optional.of(chat))
        whenever(messageService.countTotalUnreadMessagesByUserId(sellerId)).thenReturn(3L)

        chatService.markMessagesAsRead(chatId, userId)

        verify(messageService).markMessagesAsRead(chatId, userId)
        verify(notificationService).notifyUnreadCountUpdate(sellerId, 3L)
    }

    @Test
    fun `getUnreadMessageCount should return unread count`() {
        val userId = "user123"

        whenever(messageService.countTotalUnreadMessagesByUserId(userId)).thenReturn(7L)

        val result = chatService.getUnreadMessageCount(userId)

        assertEquals(7L, result)
    }

    @Test
    fun `getUnreadMessageCountForChat should return chat-specific unread count`() {
        val chatId = 1L
        val userId = "buyer123"
        val chat = ChatEntity(id = chatId, adId = 1L, buyerId = userId, sellerId = "seller456")

        whenever(chatRepository.findById(chatId)).thenReturn(Optional.of(chat))
        whenever(messageService.countUnreadMessagesByChatId(chatId, userId)).thenReturn(3L)

        val result = chatService.getUnreadMessageCountForChat(chatId, userId)

        assertEquals(3L, result)
    }

    @Test
    fun `getChatByAdId should return chats for ad and user`() {
        val adId = 1L
        val userId = "user123"
        val chats = listOf(
            ChatEntity(id = 1L, adId = adId, buyerId = userId, sellerId = "seller1")
        )

        whenever(chatRepository.findByAdIdAndUserId(adId, userId)).thenReturn(chats)

        val result = chatService.getChatByAdId(adId, userId)

        assertEquals(1, result.size)
        assertEquals(adId, result[0].adId)
    }
}
