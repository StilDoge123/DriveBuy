package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.DoorCountEntity
import com.drivebuy.service.car_info.DoorCountService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/doorCounts")
class DoorCountController(private val doorCountService: DoorCountService) {

    @GetMapping("/{id}")
    fun getDoorCountById(@PathVariable id: Long): DoorCountEntity? {
        return doorCountService.getDoorCountById(id)
    }

    @GetMapping("/{doorCount}")
    fun getDoorCountByCount(@PathVariable doorCount: String): DoorCountEntity? {
        return doorCountService.getDoorCountByCount(doorCount)
    }

    @GetMapping
    fun getAllDoorCounts(): List<DoorCountEntity?> {
        return doorCountService.getAllDoorCounts()
    }
}