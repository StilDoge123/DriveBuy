package com.drivebuy.service

import com.drivebuy.persistance.entity.CarBrandEntity
import com.drivebuy.repository.CarBrandRepository
import com.drivebuy.repository.CarModelRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class CarBrandServiceTest {

    private val carBrandRepository = mock(CarBrandRepository::class.java)
    private val carModelRepository = mock(CarModelRepository::class.java)
    private val carBrandService = CarBrandService(carBrandRepository, carModelRepository)

    @Test
    fun `getBrandById should return brand when found`() {
        val brand = CarBrandEntity(id = 1, brandName = "Toyota")
        `when`(carBrandRepository.findById(1)).thenReturn(Optional.of(brand))

        val result = carBrandService.getBrandById(1)

        assertNotNull(result)
        assertEquals("Toyota", result?.brandName)
    }

    @Test
    fun `getBrandById should return null when not found`() {
        `when`(carBrandRepository.findById(1)).thenReturn(Optional.empty())

        val result = carBrandService.getBrandById(1)

        assertNull(result)
    }

    @Test
    fun `getBrandByName should return brand when found`() {
        val brand = CarBrandEntity(id = 1, brandName = "Honda")
        `when`(carBrandRepository.findByBrandName("Honda")).thenReturn(brand)

        val result = carBrandService.getBrandByName("Honda")

        assertNotNull(result)
        assertEquals("Honda", result?.brandName)
    }

    @Test
    fun `getAllBrands should return sorted list of brands`() {
        val brands = listOf(
            CarBrandEntity(id = 1, brandName = "Toyota"),
            CarBrandEntity(id = 2, brandName = "Honda")
        )
        `when`(carBrandRepository.findAll()).thenReturn(brands)

        val result = carBrandService.getAllBrands()

        assertEquals(2, result.size)
    }
}
