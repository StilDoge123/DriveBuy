package com.drivebuy.service

import com.drivebuy.persistance.entity.ChatEntity
import com.drivebuy.persistance.entity.MessageEntity
import com.drivebuy.repository.ChatRepository
import com.drivebuy.repository.AdRepository
import com.drivebuy.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ChatService(
    private val chatRepository: ChatRepository,
    private val messageService: MessageService,
    private val adRepository: AdRepository,
    private val userRepository: UserRepository,
    private val notificationService: NotificationService
) {

    fun createOrGetChat(adId: Long, buyerId: String, sellerId: String): ChatEntity {
        val ad = adRepository.findById(adId).orElseThrow { 
            RuntimeException("Ad not found with id: $adId") 
        }
        
        if (ad.userId != sellerId) {
            throw RuntimeException("User $sellerId is not the owner of ad $adId")
        }
        
        if (buyerId == sellerId) {
            throw RuntimeException("Cannot create chat with yourself")
        }
        
        if (!userRepository.existsById(buyerId)) {
            throw RuntimeException("Buyer not found with id: $buyerId")
        }
        
        if (!userRepository.existsById(sellerId)) {
            throw RuntimeException("Seller not found with id: $sellerId")
        }
        
        val existingChat = chatRepository.findByAdIdAndBuyerIdAndSellerId(adId, buyerId, sellerId)
        if (existingChat != null) {
            return existingChat
        }
        
        val newChat = ChatEntity(
            adId = adId,
            buyerId = buyerId,
            sellerId = sellerId
        )
        
        return chatRepository.save(newChat)
    }

    fun getUserChats(userId: String): List<ChatEntity> {
        return chatRepository.findUserChatsOrderByLastMessage(userId)
    }

    fun getChatById(chatId: Long, userId: String): ChatEntity {
        val chat = chatRepository.findById(chatId).orElseThrow { 
            RuntimeException("Chat not found with id: $chatId") 
        }
        
        if (chat.buyerId != userId && chat.sellerId != userId) {
            throw RuntimeException("User $userId is not authorized to access chat $chatId")
        }
        
        return chat
    }

    fun getChatMessages(chatId: Long, userId: String): List<MessageEntity> {
        getChatById(chatId, userId)
        return messageService.getMessagesByChatId(chatId)
    }

    fun sendMessage(chatId: Long, senderId: String, content: String): MessageEntity {
        val chat = getChatById(chatId, senderId)
        
        val message = MessageEntity(
            chatId = chatId,
            senderId = senderId,
            content = content
        )
        
        val savedMessage = messageService.saveMessage(message)
        
        chat.lastMessageAt = savedMessage.timestamp
        chatRepository.save(chat)
        
        val recipientId = if (senderId == chat.buyerId) chat.sellerId else chat.buyerId
        notificationService.notifyNewMessage(chatId, savedMessage, recipientId)
        
        val unreadCount = messageService.countTotalUnreadMessagesByUserId(recipientId)
        notificationService.notifyUnreadCountUpdate(recipientId, unreadCount)
        
        return savedMessage
    }

    fun markMessagesAsRead(chatId: Long, userId: String) {
        val chat = getChatById(chatId, userId)
        messageService.markMessagesAsRead(chatId, userId)
        
        val senderId = if (userId == chat.buyerId) chat.sellerId else chat.buyerId
        val unreadCount = messageService.countTotalUnreadMessagesByUserId(senderId)
        notificationService.notifyUnreadCountUpdate(senderId, unreadCount)
    }

    fun getUnreadMessageCount(userId: String): Long {
        return messageService.countTotalUnreadMessagesByUserId(userId)
    }

    fun getUnreadMessageCountForChat(chatId: Long, userId: String): Long {
        getChatById(chatId, userId)
        return messageService.countUnreadMessagesByChatId(chatId, userId)
    }

    fun getChatByAdId(adId: Long, userId: String): List<ChatEntity> {
        return chatRepository.findByAdIdAndUserId(adId, userId)
    }
}
