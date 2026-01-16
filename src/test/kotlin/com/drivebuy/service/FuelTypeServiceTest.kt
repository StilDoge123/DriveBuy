package com.drivebuy.service

import com.drivebuy.persistance.entity.car_info.FuelTypeEntity
import com.drivebuy.repository.car_info.FuelTypeRepository
import com.drivebuy.service.car_info.FuelTypeService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class FuelTypeServiceTest {

    private val fuelTypeRepository = mock(FuelTypeRepository::class.java)
    private val fuelTypeService = FuelTypeService(fuelTypeRepository)

    @Test
    fun `getFuelTypeById should return fuel type when found`() {
        val fuelType = FuelTypeEntity(id = 1, fuelTypeName = "Diesel")
        `when`(fuelTypeRepository.findById(1)).thenReturn(Optional.of(fuelType))

        val result = fuelTypeService.getFuelTypeById(1)

        assertNotNull(result)
        assertEquals("Diesel", result?.fuelTypeName)
    }

    @Test
    fun `getFuelTypeById should return null when not found`() {
        `when`(fuelTypeRepository.findById(1)).thenReturn(Optional.empty())

        val result = fuelTypeService.getFuelTypeById(1)

        assertNull(result)
    }

    @Test
    fun `getAllFuelTypes should return list of fuel types`() {
        val fuelTypes = listOf(
            FuelTypeEntity(id = 1, fuelTypeName = "Diesel"),
            FuelTypeEntity(id = 2, fuelTypeName = "Petrol")
        )
        `when`(fuelTypeRepository.findAll()).thenReturn(fuelTypes)

        val result = fuelTypeService.getAllFuelTypes()

        assertEquals(2, result.size)
    }
}
