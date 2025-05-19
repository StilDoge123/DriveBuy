package com.drivebuy.repository.car_info

import com.drivebuy.persistance.entity.car_info.ColorEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ColorRepository : JpaRepository<ColorEntity, Long> {
    fun findByColorName(colorName: String): ColorEntity?
}