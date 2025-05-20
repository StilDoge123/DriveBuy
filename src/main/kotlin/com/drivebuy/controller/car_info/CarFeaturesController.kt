package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.CarFeaturesEntity
import com.drivebuy.service.car_info.CarFeaturesService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/carFeatures")
class CarFeaturesController (private val carFeaturesService: CarFeaturesService) {
    @GetMapping("/id/{id}")
    fun getCarFeatureById(@PathVariable id: Long): CarFeaturesEntity? {
        return carFeaturesService.getCarFeatureById(id)
    }

    @GetMapping("/carFeature/{carFeatureName}")
    fun getCarFeatureByFeatureName(@PathVariable carFeatureName: String): CarFeaturesEntity? {
        return carFeaturesService.getCarConditionByConditionName(carFeatureName)
    }

    @GetMapping
    fun getAllCarFeatures(): List<CarFeaturesEntity?> {
        return carFeaturesService.getAllCarFeatures()
    }
}