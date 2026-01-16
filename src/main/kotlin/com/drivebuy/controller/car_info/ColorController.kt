package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.ColorEntity
import com.drivebuy.security.RequiresApiKey
import com.drivebuy.service.car_info.ColorService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/colors")
class ColorController(private val colorService: ColorService) {

    @GetMapping("/id/{id}")
    fun getColorById(@PathVariable id: Long): ColorEntity? {
        return colorService.getColorById(id)
    }

    @GetMapping("/colorName/{colorName}")
    fun getColorByName(@PathVariable colorName: String): ColorEntity? {
        return colorService.getColorByName(colorName)
    }

    @GetMapping
    fun getAllColors(): List<ColorEntity?> {
        return colorService.getAllColors()
    }

    @RequiresApiKey
    @PutMapping("/update/{colorId}")
    fun updateColor(
        @PathVariable colorId: Long,
        @RequestParam colorName: String): ColorEntity {
        return colorService.updateColor(colorId, colorName)
    }

    @RequiresApiKey
    @DeleteMapping("/delete/{colorId}")
    fun deleteColor(@PathVariable colorId: Long) {
        colorService.deleteColor(colorId)
    }
}