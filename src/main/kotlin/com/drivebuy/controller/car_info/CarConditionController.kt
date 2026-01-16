package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.CarConditionEntity
import com.drivebuy.security.RequiresApiKey
import com.drivebuy.service.car_info.CarConditionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/carConditions")
class CarConditionController(private val carConditionService: CarConditionService) {
    @GetMapping("/id/{id}")
    fun getCarConditionById(@PathVariable id: Long): CarConditionEntity? {
        return carConditionService.getCarConditionById(id)
    }

    @GetMapping("/carCondition/{carConditionName}")
    fun getCarConditionByConditionName(@PathVariable carConditionName: String): CarConditionEntity? {
        return carConditionService.getCarConditionByConditionName(carConditionName)
    }

    @GetMapping
    fun getAllCarConditions(): List<CarConditionEntity?> {
        return carConditionService.getAllCarConditions()
    }

    @RequiresApiKey
    @PutMapping("/update/{carConditionId}")
    fun updateCarCondition(
        @PathVariable carConditionId: Long,
        @RequestParam carConditionName: String): CarConditionEntity {
        return carConditionService.updateCarCondition(carConditionId, carConditionName)
    }

    @RequiresApiKey
    @DeleteMapping("/delete/{carConditionId}")
    fun deleteCarCondition(@PathVariable carConditionId: Long) {
        carConditionService.deleteCarCondition(carConditionId)
    }
}