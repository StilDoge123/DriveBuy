package com.drivebuy.service

import com.drivebuy.persistance.entity.car_info.CylinderCountEntity
import com.drivebuy.repository.car_info.CylinderCountRepository
import com.drivebuy.service.car_info.CylinderCountService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class CylinderCountServiceTest {

    private val cylinderCountRepository = mock(CylinderCountRepository::class.java)
    private val cylinderCountService = CylinderCountService(cylinderCountRepository)

    @Test
    fun `getCylinderCountById should return cylinder count when found`() {
        val cylinderCount = CylinderCountEntity(id = 1, cylinderCount = "4")
        `when`(cylinderCountRepository.findById(1)).thenReturn(Optional.of(cylinderCount))

        val result = cylinderCountService.getCylinderCountById(1)

        assertNotNull(result)
        assertEquals("4", result?.cylinderCount)
    }

    @Test
    fun `getCylinderCountById should return null when not found`() {
        `when`(cylinderCountRepository.findById(1)).thenReturn(Optional.empty())

        val result = cylinderCountService.getCylinderCountById(1)

        assertNull(result)
    }

    @Test
    fun `getAllCylinderCounts should return list`() {
        val cylinderCounts = listOf(
            CylinderCountEntity(id = 1, cylinderCount = "4"),
            CylinderCountEntity(id = 2, cylinderCount = "6")
        )
        `when`(cylinderCountRepository.findAll()).thenReturn(cylinderCounts)

        val result = cylinderCountService.getAllCylinderCounts()

        assertEquals(2, result.size)
    }
}
