package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.SteeringPositionEntity
import com.drivebuy.service.car_info.SteeringPositionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/steeringPositions")
class SteeringPositionTypes(private val steeringPositionService: SteeringPositionService) {

    @GetMapping("/id/{id}")
    fun getSteeringPositionById(@PathVariable id: Long): SteeringPositionEntity? {
        return steeringPositionService.getSteeringPositionById(id)
    }

    @GetMapping("/steeringPosition/{steeringPositionName}")
    fun getSteeringPositionByPositionName(@PathVariable steeringPositionName: String): SteeringPositionEntity? {
        return steeringPositionService.getSteeringPositionByPositionName(steeringPositionName)
    }

    @GetMapping
    fun getAllSteeringPositions(): List<SteeringPositionEntity?> {
        return steeringPositionService.getAllSteeringPositions()
    }
}