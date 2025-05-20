package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.BodyTypeEntity
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
}