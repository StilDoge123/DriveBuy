package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.BodyTypeEntity
import com.drivebuy.persistance.entity.car_info.CarConditionEntity
import com.drivebuy.service.car_info.CarConditionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/carConditions")
class CarConditionController(private val carConditionService: CarConditionService) {
    @GetMapping("/{id}")
    fun getCarConditionById(@PathVariable id: Long): CarConditionEntity? {
        return carConditionService.getCarConditionById(id)
    }

    @GetMapping("/{carConditionName}")
    fun getCarConditionByConditionName(@PathVariable carConditionName: String): CarConditionEntity? {
        return carConditionService.getCarConditionByConditionName(carConditionName)
    }

    @GetMapping
    fun getAllCarConditions(): List<CarConditionEntity?> {
        return carConditionService.getAllCarConditions()
    }
}