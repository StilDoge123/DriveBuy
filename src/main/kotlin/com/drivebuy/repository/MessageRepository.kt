package com.drivebuy.repository

import com.drivebuy.persistance.entity.MessageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : JpaRepository<MessageEntity, Long> {
    
    fun findByChatIdOrderByTimestampAsc(chatId: Long): List<MessageEntity>
    
    fun findByChatIdOrderByTimestampDesc(chatId: Long): List<MessageEntity>
    
    @Query("SELECT m FROM MessageEntity m WHERE m.chatId = :chatId AND m.senderId != :userId AND m.isRead = false")
    fun findUnreadMessagesByChatIdAndUserId(@Param("chatId") chatId: Long, @Param("userId") userId: String): List<MessageEntity>
    
    @Query("SELECT COUNT(m) FROM MessageEntity m WHERE m.chatId = :chatId AND m.senderId != :userId AND m.isRead = false")
    fun countUnreadMessagesByChatIdAndUserId(@Param("chatId") chatId: Long, @Param("userId") userId: String): Long
    
    @Query("SELECT COUNT(m) FROM MessageEntity m JOIN ChatEntity c ON m.chatId = c.id WHERE (c.buyerId = :userId OR c.sellerId = :userId) AND m.senderId != :userId AND m.isRead = false")
    fun countTotalUnreadMessagesByUserId(@Param("userId") userId: String): Long
    
    fun findTop1ByChatIdOrderByTimestampDesc(chatId: Long): MessageEntity?
    
    fun deleteAllByChatId(chatId: Long)
    
    fun deleteAllByChatIdIn(chatIds: List<Long>)
}
