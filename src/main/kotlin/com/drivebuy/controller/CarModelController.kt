package com.drivebuy.controller

import com.drivebuy.persistance.entity.CarModelEntity
import com.drivebuy.security.RequiresApiKey
import com.drivebuy.service.CarModelService
import org.springframework.web.bind.annotation.*

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

    @RequiresApiKey
    @PostMapping("/create")
    fun createCarModel(
        @RequestParam modelName: String,
        @RequestParam brandId: Long): CarModelEntity {
        return carModelService.createCarModel(modelName, brandId)
    }

    @RequiresApiKey
    @PostMapping("/addModels/{brandId}")
    fun addModelsToBrand(
        @PathVariable brandId: Long,
        @RequestBody modelNames: List<String>): List<CarModelEntity> {
        return carModelService.addModelsToBrand(brandId, modelNames)
    }

    @RequiresApiKey
    @PutMapping("/update/{modelId}")
    fun updateCarModel(
        @PathVariable modelId: Long,
        @RequestParam modelName: String,
        @RequestParam brandId: Long): CarModelEntity {
        return carModelService.updateCarModel(modelId, modelName, brandId)
    }

    @RequiresApiKey
    @DeleteMapping("/delete/{modelId}")
    fun deleteCarModel(@PathVariable modelId: Long) {
        carModelService.deleteCarModel(modelId)
    }
}