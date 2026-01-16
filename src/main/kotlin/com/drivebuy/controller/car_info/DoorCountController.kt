package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.DoorCountEntity
import com.drivebuy.security.RequiresApiKey
import com.drivebuy.service.car_info.DoorCountService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/doorCounts")
class DoorCountController(private val doorCountService: DoorCountService) {

    @GetMapping("/id/{id}")
    fun getDoorCountById(@PathVariable id: Long): DoorCountEntity? {
        return doorCountService.getDoorCountById(id)
    }

    @GetMapping("/doorCount/{doorCount}")
    fun getDoorCountByCount(@PathVariable doorCount: String): DoorCountEntity? {
        return doorCountService.getDoorCountByCount(doorCount)
    }

    @GetMapping
    fun getAllDoorCounts(): List<DoorCountEntity?> {
        return doorCountService.getAllDoorCounts()
    }

    @RequiresApiKey
    @PutMapping("/update/{doorCountId}")
    fun updateDoorCount(
        @PathVariable doorCountId: Long,
        @RequestParam doorCount: String): DoorCountEntity {
        return doorCountService.updateDoorCount(doorCountId, doorCount)
    }

    @RequiresApiKey
    @DeleteMapping("/delete/{doorCountId}")
    fun deleteDoorCount(@PathVariable doorCountId: Long) {
        doorCountService.deleteDoorCount(doorCountId)
    }
}