package com.drivebuy.repository

import com.drivebuy.persistance.entity.ChatEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : JpaRepository<ChatEntity, Long> {
    
    fun findByAdIdAndBuyerIdAndSellerId(adId: Long, buyerId: String, sellerId: String): ChatEntity?
    
    fun findByBuyerIdOrSellerId(buyerId: String, sellerId: String): List<ChatEntity>
    
    fun findByBuyerId(buyerId: String): List<ChatEntity>
    
    fun findBySellerId(sellerId: String): List<ChatEntity>
    
    @Query("SELECT c FROM ChatEntity c WHERE c.adId = :adId AND (c.buyerId = :userId OR c.sellerId = :userId)")
    fun findByAdIdAndUserId(@Param("adId") adId: Long, @Param("userId") userId: String): List<ChatEntity>
    
    @Query("SELECT c FROM ChatEntity c WHERE (c.buyerId = :userId OR c.sellerId = :userId) ORDER BY c.lastMessageAt DESC")
    fun findUserChatsOrderByLastMessage(@Param("userId") userId: String): List<ChatEntity>
    
    fun existsByAdIdAndBuyerIdAndSellerId(adId: Long, buyerId: String, sellerId: String): Boolean
}
