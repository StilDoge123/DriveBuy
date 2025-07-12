package com.drivebuy.repository

import com.drivebuy.persistance.entity.CarAdEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface AdRepository : JpaRepository<CarAdEntity, Long>, JpaSpecificationExecutor<CarAdEntity> {
    fun findByUserId(userId: String): List<CarAdEntity>
    fun findEntityById(id: Long): CarAdEntity
}