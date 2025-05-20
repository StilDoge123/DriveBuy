package com.drivebuy.controller

import com.drivebuy.persistance.entity.CarModelEntity
import com.drivebuy.service.CarModelService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/models")
class CarModelController(private val carModelService: CarModelService) {
    @GetMapping("/id/{id}")
    fun getModelById(@PathVariable id: Long): CarModelEntity? {
        return carModelService.getModelById(id)
    }

    @GetMapping("/brandId/{brandId}")
    fun getAllModelsByBrandId(@PathVariable brandId: Long): List<CarModelEntity?> {
        return carModelService.getAllModelsByBrandId(brandId)
    }
}