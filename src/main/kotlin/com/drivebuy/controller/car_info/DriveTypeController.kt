package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.DriveTypeEntity
import com.drivebuy.service.car_info.DriveTypeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/driveTypes")
class DriveTypeController(private val driveTypeService: DriveTypeService) {

    @GetMapping("/{id}")
    fun getDriveTypeById(@PathVariable id: Long): DriveTypeEntity? {
        return driveTypeService.getDriveTypeById(id)
    }

    @GetMapping("/{driveTypeName}")
    fun getDriveTypeByTypeName(@PathVariable driveTypeName: String): DriveTypeEntity? {
        return driveTypeService.getDriveTypeByTypeName(driveTypeName)
    }

    @GetMapping
    fun getAllDriveTypes(): List<DriveTypeEntity?> {
        return driveTypeService.getAllDriveTypes()
    }
}