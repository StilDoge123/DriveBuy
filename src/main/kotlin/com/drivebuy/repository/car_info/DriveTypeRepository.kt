package com.drivebuy.repository.car_info

import com.drivebuy.persistance.entity.car_info.DriveTypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DriveTypeRepository : JpaRepository<DriveTypeEntity, Long> {
    fun findByDriveTypeName(driveTypeName: String): DriveTypeEntity?
}