package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.CylinderCountEntity
import com.drivebuy.service.car_info.CylinderCountService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cylinderCounts")
class CylinderCountController(private val cylinderCountService: CylinderCountService) {

    @GetMapping("/{id}")
    fun getCylinderCountById(@PathVariable id: Long): CylinderCountEntity? {
        return cylinderCountService.getCylinderCountById(id)
    }

    @GetMapping("/{cylinderCount}")
    fun getCylinderCountByCount(@PathVariable cylinderCount: String): CylinderCountEntity? {
        return cylinderCountService.getCylinderCountByCount(cylinderCount)
    }

    @GetMapping
    fun getAllCylinderCounts(): List<CylinderCountEntity?> {
        return cylinderCountService.getAllCylinderCounts()
    }
}