package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.SteeringPositionEntity
import com.drivebuy.security.RequiresApiKey
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

    @RequiresApiKey
    @PutMapping("/update/{steeringPositionId}")
    fun updateSteeringPosition(
        @PathVariable steeringPositionId: Long,
        @RequestParam steeringPositionName: String): SteeringPositionEntity {
        return steeringPositionService.updateSteeringPosition(steeringPositionId, steeringPositionName)
    }

    @RequiresApiKey
    @DeleteMapping("/delete/{steeringPositionId}")
    fun deleteSteeringPosition(@PathVariable steeringPositionId: Long) {
        steeringPositionService.deleteSteeringPosition(steeringPositionId)
    }
}