package com.drivebuy.service

import com.drivebuy.persistance.entity.car_info.SteeringPositionEntity
import com.drivebuy.repository.car_info.SteeringPositionRepository
import com.drivebuy.service.car_info.SteeringPositionService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class SteeringPositionServiceTest {

    private val steeringPositionRepository = mock(SteeringPositionRepository::class.java)
    private val steeringPositionService = SteeringPositionService(steeringPositionRepository)

    @Test
    fun `getSteeringPositionById should return steering position when found`() {
        val steeringPosition = SteeringPositionEntity(id = 1, steeringPositionName = "Left")
        `when`(steeringPositionRepository.findById(1)).thenReturn(Optional.of(steeringPosition))

        val result = steeringPositionService.getSteeringPositionById(1)

        assertNotNull(result)
        assertEquals("Left", result?.steeringPositionName)
    }

    @Test
    fun `getSteeringPositionById should return null when not found`() {
        `when`(steeringPositionRepository.findById(1)).thenReturn(Optional.empty())

        val result = steeringPositionService.getSteeringPositionById(1)

        assertNull(result)
    }

    @Test
    fun `getAllSteeringPositions should return list`() {
        val steeringPositions = listOf(
            SteeringPositionEntity(id = 1, steeringPositionName = "Left"),
            SteeringPositionEntity(id = 2, steeringPositionName = "Right")
        )
        `when`(steeringPositionRepository.findAll()).thenReturn(steeringPositions)

        val result = steeringPositionService.getAllSteeringPositions()

        assertEquals(2, result.size)
    }
}
