package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.DriveTypeEntity
import com.drivebuy.security.RequiresApiKey
import com.drivebuy.service.car_info.DriveTypeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/driveTypes")
class DriveTypeController(private val driveTypeService: DriveTypeService) {

    @GetMapping("/id/{id}")
    fun getDriveTypeById(@PathVariable id: Long): DriveTypeEntity? {
        return driveTypeService.getDriveTypeById(id)
    }

    @GetMapping("/driveType/{driveTypeName}")
    fun getDriveTypeByTypeName(@PathVariable driveTypeName: String): DriveTypeEntity? {
        return driveTypeService.getDriveTypeByTypeName(driveTypeName)
    }

    @GetMapping
    fun getAllDriveTypes(): List<DriveTypeEntity?> {
        return driveTypeService.getAllDriveTypes()
    }

    @RequiresApiKey
    @PutMapping("/update/{driveTypeId}")
    fun updateDriveType(
        @PathVariable driveTypeId: Long,
        @RequestParam driveTypeName: String): DriveTypeEntity {
        return driveTypeService.updateDriveType(driveTypeId, driveTypeName)
    }

    @RequiresApiKey
    @DeleteMapping("/delete/{driveTypeId}")
    fun deleteDriveType(@PathVariable driveTypeId: Long) {
        driveTypeService.deleteDriveType(driveTypeId)
    }
}