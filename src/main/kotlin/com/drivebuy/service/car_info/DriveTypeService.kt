package com.drivebuy.service.car_info

import com.drivebuy.persistance.entity.car_info.DriveTypeEntity
import com.drivebuy.repository.car_info.DriveTypeRepository
import org.springframework.stereotype.Service


@Service
class DriveTypeService(private val driveTypeRepository: DriveTypeRepository) {
    fun getDriveTypeById(id: Long): DriveTypeEntity? {
        return driveTypeRepository.findById(id).orElse(null)
    }

    fun getDriveTypeByTypeName(driveTypeName: String): DriveTypeEntity? {
        return driveTypeRepository.findByDriveTypeName(driveTypeName)
    }

    fun getAllDriveTypes(): List<DriveTypeEntity?> {
        return driveTypeRepository.findAll()
    }
}