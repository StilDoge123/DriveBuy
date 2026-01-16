package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.BodyTypeEntity
import com.drivebuy.security.RequiresApiKey
import com.drivebuy.service.car_info.BodyTypeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bodyTypes")
class BodyTypeController(private val bodyTypeService: BodyTypeService) {
    @GetMapping("/id/{id}")
    fun getBodyTypeById(@PathVariable id: Long): BodyTypeEntity? {
        return bodyTypeService.getBodyTypeById(id)
    }

    @GetMapping("/bodyType/{bodyTypeName}")
    fun getBodyTypeByTypeName(@PathVariable bodyTypeName: String): BodyTypeEntity? {
        return bodyTypeService.getBodyTypeByBodyTypeName(bodyTypeName)
    }

    @GetMapping
    fun getAllBodyTypes(): List<BodyTypeEntity?> {
        return bodyTypeService.getAllBodyTypes()
    }

    @RequiresApiKey
    @PutMapping("/update/{bodyTypeId}")
    fun updateBodyType(
        @PathVariable bodyTypeId: Long,
        @RequestParam bodyTypeName: String): BodyTypeEntity {
        return bodyTypeService.updateBodyType(bodyTypeId, bodyTypeName)
    }

    @RequiresApiKey
    @DeleteMapping("/delete/{bodyTypeId}")
    fun deleteBodyType(@PathVariable bodyTypeId: Long) {
        bodyTypeService.deleteBodyType(bodyTypeId)
    }
}