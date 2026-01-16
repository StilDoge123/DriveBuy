package com.drivebuy.service.car_info

import com.drivebuy.persistance.entity.car_info.ColorEntity
import com.drivebuy.repository.car_info.ColorRepository
import org.springframework.stereotype.Service


@Service
class ColorService(private val colorRepository: ColorRepository) {
    fun getColorById(id: Long): ColorEntity? {
        return colorRepository.findById(id).orElse(null)
    }

    fun getColorByName(colorName: String): ColorEntity? {
        return colorRepository.findByColorName(colorName)
    }

    fun getAllColors(): List<ColorEntity?> {
        return colorRepository.findAll()
    }

    fun updateColor(id: Long, name: String): ColorEntity {
        val color = colorRepository.findById(id)
            .orElseThrow { NoSuchElementException("Color not found") }

        color.colorName = name
        return colorRepository.save(color)
    }

    fun deleteColor(id: Long) {
        val color = colorRepository.findById(id)
            .orElseThrow { NoSuchElementException("Color not found") }

        colorRepository.delete(color)
    }
}