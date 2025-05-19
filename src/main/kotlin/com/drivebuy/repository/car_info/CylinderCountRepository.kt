package com.drivebuy.repository.car_info

import com.drivebuy.persistance.entity.car_info.CylinderCountEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CylinderCountRepository : JpaRepository<CylinderCountEntity, Long> {
    fun findByCylinderCount(cylinderCount: String): CylinderCountEntity?
}