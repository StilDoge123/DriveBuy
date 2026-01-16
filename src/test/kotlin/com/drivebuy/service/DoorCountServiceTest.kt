package com.drivebuy.service

import com.drivebuy.persistance.entity.car_info.DoorCountEntity
import com.drivebuy.repository.car_info.DoorCountRepository
import com.drivebuy.service.car_info.DoorCountService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class DoorCountServiceTest {

    private val doorCountRepository = mock(DoorCountRepository::class.java)
    private val doorCountService = DoorCountService(doorCountRepository)

    @Test
    fun `getDoorCountById should return door count when found`() {
        val doorCount = DoorCountEntity(id = 1, doorCount = "4")
        `when`(doorCountRepository.findById(1)).thenReturn(Optional.of(doorCount))

        val result = doorCountService.getDoorCountById(1)

        assertNotNull(result)
        assertEquals("4", result?.doorCount)
    }

    @Test
    fun `getDoorCountById should return null when not found`() {
        `when`(doorCountRepository.findById(1)).thenReturn(Optional.empty())

        val result = doorCountService.getDoorCountById(1)

        assertNull(result)
    }

    @Test
    fun `getAllDoorCounts should return list`() {
        val doorCounts = listOf(
            DoorCountEntity(id = 1, doorCount = "2"),
            DoorCountEntity(id = 2, doorCount = "4")
        )
        `when`(doorCountRepository.findAll()).thenReturn(doorCounts)

        val result = doorCountService.getAllDoorCounts()

        assertEquals(2, result.size)
    }
}
