package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.ColorEntity
import com.drivebuy.service.car_info.ColorService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/colors")
class ColorController(private val colorService: ColorService) {

    @GetMapping("/{id}")
    fun getColorById(@PathVariable id: Long): ColorEntity? {
        return colorService.getColorById(id)
    }

    @GetMapping("/{colorName}")
    fun getColorByName(@PathVariable colorName: String): ColorEntity? {
        return colorService.getColorByName(colorName)
    }

    @GetMapping
    fun getAllColors(): List<ColorEntity?> {
        return colorService.getAllColors()
    }
}