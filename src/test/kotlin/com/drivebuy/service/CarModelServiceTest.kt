package com.drivebuy.service

import com.drivebuy.persistance.entity.CarBrandEntity
import com.drivebuy.persistance.entity.CarModelEntity
import com.drivebuy.repository.CarBrandRepository
import com.drivebuy.repository.CarModelRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class CarModelServiceTest {

    private val carModelRepository = mock(CarModelRepository::class.java)
    private val carBrandService = mock(CarBrandService::class.java)
    private val carModelService = CarModelService(carModelRepository, carBrandService)

    @Test
    fun `getModelById should return model when found`() {
        val brand = CarBrandEntity(id = 1, brandName = "Toyota")
        val model = CarModelEntity(id = 1, brand = brand, modelName = "Camry")
        `when`(carModelRepository.findById(1)).thenReturn(Optional.of(model))

        val result = carModelService.getModelById(1)

        assertNotNull(result)
        assertEquals("Camry", result?.modelName)
    }

    @Test
    fun `getModelById should return null when not found`() {
        `when`(carModelRepository.findById(1)).thenReturn(Optional.empty())

        val result = carModelService.getModelById(1)

        assertNull(result)
    }

    @Test
    fun `getAllModelsByBrandId should return models for brand`() {
        val brand = CarBrandEntity(id = 1, brandName = "Toyota")
        val models = listOf(
            CarModelEntity(id = 1, brand = brand, modelName = "Camry"),
            CarModelEntity(id = 2, brand = brand, modelName = "Corolla")
        )
        `when`(carModelRepository.findAllByBrandId(1)).thenReturn(models)

        val result = carModelService.getAllModelsByBrandId(1)

        assertEquals(2, result.size)
    }
}
