package com.drivebuy.service

import com.drivebuy.persistance.entity.MessageEntity
import com.drivebuy.repository.MessageRepository
import com.drivebuy.repository.ChatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MessageService(
    private val messageRepository: MessageRepository,
    private val chatRepository: ChatRepository
) {

    fun getMessagesByChatId(chatId: Long): List<MessageEntity> {
        return messageRepository.findByChatIdOrderByTimestampAsc(chatId)
    }

    fun getLatestMessageByChatId(chatId: Long): MessageEntity? {
        return messageRepository.findTop1ByChatIdOrderByTimestampDesc(chatId)
    }

    fun markMessageAsRead(messageId: Long) {
        val message = messageRepository.findById(messageId).orElseThrow { 
            RuntimeException("Message not found with id: $messageId") 
        }
        message.isRead = true
        messageRepository.save(message)
    }

    fun markMessagesAsRead(chatId: Long, userId: String) {
        val unreadMessages = messageRepository.findUnreadMessagesByChatIdAndUserId(chatId, userId)
        unreadMessages.forEach { message ->
            message.isRead = true
        }
        messageRepository.saveAll(unreadMessages)
    }

    fun getUnreadMessagesByChatId(chatId: Long, userId: String): List<MessageEntity> {
        return messageRepository.findUnreadMessagesByChatIdAndUserId(chatId, userId)
    }

    fun countUnreadMessagesByChatId(chatId: Long, userId: String): Long {
        return messageRepository.countUnreadMessagesByChatIdAndUserId(chatId, userId)
    }

    fun saveMessage(message: MessageEntity): MessageEntity {
        return messageRepository.save(message)
    }

    fun countTotalUnreadMessagesByUserId(userId: String): Long {
        return messageRepository.countTotalUnreadMessagesByUserId(userId)
    }
}
