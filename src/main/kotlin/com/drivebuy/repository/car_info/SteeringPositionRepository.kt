package com.drivebuy.repository.car_info

import com.drivebuy.persistance.entity.car_info.SteeringPositionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SteeringPositionRepository : JpaRepository<SteeringPositionEntity, Long> {
    fun findBySteeringPositionName(steeringPositionName: String): SteeringPositionEntity?
}