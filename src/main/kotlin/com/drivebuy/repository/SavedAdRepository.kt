package com.drivebuy.repository

import com.drivebuy.persistance.entity.SavedAdEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SavedAdRepository : JpaRepository<SavedAdEntity, Long> {
    fun findByUserId(userId: String): List<SavedAdEntity>
    fun existsByUserIdAndAdId(userId: String, adId: Long): Boolean
}