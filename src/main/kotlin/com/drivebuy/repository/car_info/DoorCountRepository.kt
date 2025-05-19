package com.drivebuy.repository.car_info

import com.drivebuy.persistance.entity.car_info.DoorCountEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DoorCountRepository : JpaRepository<DoorCountEntity, Long> {
    fun findByDoorCount(doorCount: String): DoorCountEntity?
}