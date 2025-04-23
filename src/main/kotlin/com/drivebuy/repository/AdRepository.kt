package com.drivebuy.repository

import com.drivebuy.persistance.entity.CarAdEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdRepository : JpaRepository<CarAdEntity, Long> {
    fun findByUserId(userId: String): List<CarAdEntity>
}
