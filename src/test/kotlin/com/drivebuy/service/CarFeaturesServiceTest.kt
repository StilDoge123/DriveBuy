package com.drivebuy.service

import com.drivebuy.persistance.entity.car_info.CarFeaturesEntity
import com.drivebuy.repository.car_info.CarFeaturesRepository
import com.drivebuy.service.car_info.CarFeaturesService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class CarFeaturesServiceTest {

    private val carFeaturesRepository = mock(CarFeaturesRepository::class.java)
    private val carFeaturesService = CarFeaturesService(carFeaturesRepository)

    @Test
    fun `getCarFeatureById should return car feature when found`() {
        val feature = CarFeaturesEntity(id = 1, featureName = "Sunroof")
        `when`(carFeaturesRepository.findById(1)).thenReturn(Optional.of(feature))

        val result = carFeaturesService.getCarFeatureById(1)

        assertNotNull(result)
        assertEquals("Sunroof", result?.featureName)
    }

    @Test
    fun `getCarFeatureById should return null when not found`() {
        `when`(carFeaturesRepository.findById(1)).thenReturn(Optional.empty())

        val result = carFeaturesService.getCarFeatureById(1)

        assertNull(result)
    }

    @Test
    fun `getAllCarFeatures should return list`() {
        val features = listOf(
            CarFeaturesEntity(id = 1, featureName = "Sunroof"),
            CarFeaturesEntity(id = 2, featureName = "GPS")
        )
        `when`(carFeaturesRepository.findAll()).thenReturn(features)

        val result = carFeaturesService.getAllCarFeatures()

        assertEquals(2, result.size)
    }
}
