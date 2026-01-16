package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.TransmissionTypeEntity
import com.drivebuy.security.RequiresApiKey
import com.drivebuy.service.car_info.TransmissionTypeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transmissionTypes")
class TransmissionTypeController(private val transmissionTypeService: TransmissionTypeService) {

    @GetMapping("/id/{id}")
    fun getTransmissionTypeById(@PathVariable id: Long): TransmissionTypeEntity? {
        return transmissionTypeService.getTransmissionTypeById(id)
    }

    @GetMapping("/transmissionType/{transmissionTypeName}")
    fun getTransmissionTypeByTypeName(@PathVariable transmissionTypeName: String): TransmissionTypeEntity? {
        return transmissionTypeService.getTransmissionTypeByTypeName(transmissionTypeName)
    }

    @GetMapping
    fun getAllTransmissionTypes(): List<TransmissionTypeEntity?> {
        return transmissionTypeService.getAllTransmissionTypes()
    }

    @RequiresApiKey
    @PutMapping("/update/{transmissionTypeId}")
    fun updateTransmissionType(
        @PathVariable transmissionTypeId: Long,
        @RequestParam transmissionTypeName: String): TransmissionTypeEntity {
        return transmissionTypeService.updateTransmissionType(transmissionTypeId, transmissionTypeName)
    }

    @RequiresApiKey
    @DeleteMapping("/delete/{transmissionTypeId}")
    fun deleteTransmissionType(@PathVariable transmissionTypeId: Long) {
        transmissionTypeService.deleteTransmissionType(transmissionTypeId)
    }
}