package com.drivebuy.repository.car_info

import com.drivebuy.persistance.entity.car_info.FuelTypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FuelTypeRepository : JpaRepository<FuelTypeEntity, Long> {
    fun findByFuelTypeName(fuelTypeName: String): FuelTypeEntity?
}