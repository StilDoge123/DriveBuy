package com.drivebuy.service

import com.drivebuy.persistance.entity.car_info.ColorEntity
import com.drivebuy.repository.car_info.ColorRepository
import com.drivebuy.service.car_info.ColorService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class ColorServiceTest {

    private val colorRepository = mock(ColorRepository::class.java)
    private val colorService = ColorService(colorRepository)

    @Test
    fun `getColorById should return color when found`() {
        val colorEntity = ColorEntity(id = 1, colorName = "Red")
        `when`(colorRepository.findById(1)).thenReturn(Optional.of(colorEntity))

        val result = colorService.getColorById(1)

        assertNotNull(result)
        assertEquals("Red", result?.colorName)
    }

    @Test
    fun `getColorById should return null when not found`() {
        `when`(colorRepository.findById(1)).thenReturn(Optional.empty())

        val result = colorService.getColorById(1)

        assertNull(result)
    }

    @Test
    fun `getColorByName should return color when found`() {
        val colorEntity = ColorEntity(id = 1, colorName = "Blue")
        `when`(colorRepository.findByColorName("Blue")).thenReturn(colorEntity)

        val result = colorService.getColorByName("Blue")

        assertNotNull(result)
        assertEquals("Blue", result?.colorName)
    }

    @Test
    fun `getAllColors should return list of colors`() {
        val colors = listOf(
            ColorEntity(id = 1, colorName = "Red"),
            ColorEntity(id = 2, colorName = "Blue")
        )
        `when`(colorRepository.findAll()).thenReturn(colors)

        val result = colorService.getAllColors()

        assertEquals(2, result.size)
    }
}
