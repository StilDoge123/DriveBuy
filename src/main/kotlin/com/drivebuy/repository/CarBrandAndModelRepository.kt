package com.drivebuy.repository

import com.drivebuy.persistance.entity.CarBrandEntity
import com.drivebuy.persistance.entity.CarModelEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CarBrandRepository : JpaRepository<CarBrandEntity, Long> {
    fun findByBrandName(brandName: String): CarBrandEntity?
}

interface CarModelRepository : JpaRepository<CarModelEntity, Long> {
    fun findAllByBrandId(id: Long): List<CarModelEntity>
}