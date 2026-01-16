package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.CylinderCountEntity
import com.drivebuy.security.RequiresApiKey
import com.drivebuy.service.car_info.CylinderCountService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cylinderCounts")
class CylinderCountController(private val cylinderCountService: CylinderCountService) {

    @GetMapping("/id/{id}")
    fun getCylinderCountById(@PathVariable id: Long): CylinderCountEntity? {
        return cylinderCountService.getCylinderCountById(id)
    }

    @GetMapping("/cylinderCount/{cylinderCount}")
    fun getCylinderCountByCount(@PathVariable cylinderCount: String): CylinderCountEntity? {
        return cylinderCountService.getCylinderCountByCount(cylinderCount)
    }

    @GetMapping
    fun getAllCylinderCounts(): List<CylinderCountEntity?> {
        return cylinderCountService.getAllCylinderCounts()
    }

    @RequiresApiKey
    @PutMapping("/update/{cylinderCountId}")
    fun updateCylinderCount(
        @PathVariable cylinderCountId: Long,
        @RequestParam cylinderCount: String): CylinderCountEntity {
        return cylinderCountService.updateCylinderCount(cylinderCountId, cylinderCount)
    }

    @RequiresApiKey
    @DeleteMapping("/delete/{cylinderCountId}")
    fun deleteCylinderCount(@PathVariable cylinderCountId: Long) {
        cylinderCountService.deleteCylinderCount(cylinderCountId)
    }
}