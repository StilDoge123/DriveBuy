package com.drivebuy.service

import com.drivebuy.persistance.entity.car_info.CarConditionEntity
import com.drivebuy.repository.car_info.CarConditionRepository
import com.drivebuy.service.car_info.CarConditionService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class CarConditionServiceTest {

    private val carConditionRepository = mock(CarConditionRepository::class.java)
    private val carConditionService = CarConditionService(carConditionRepository)

    @Test
    fun `getCarConditionById should return car condition when found`() {
        val condition = CarConditionEntity(id = 1, conditionName = "New")
        `when`(carConditionRepository.findById(1)).thenReturn(Optional.of(condition))

        val result = carConditionService.getCarConditionById(1)

        assertNotNull(result)
        assertEquals("New", result?.conditionName)
    }

    @Test
    fun `getCarConditionById should return null when not found`() {
        `when`(carConditionRepository.findById(1)).thenReturn(Optional.empty())

        val result = carConditionService.getCarConditionById(1)

        assertNull(result)
    }

    @Test
    fun `getAllCarConditions should return list`() {
        val conditions = listOf(
            CarConditionEntity(id = 1, conditionName = "New"),
            CarConditionEntity(id = 2, conditionName = "Used")
        )
        `when`(carConditionRepository.findAll()).thenReturn(conditions)

        val result = carConditionService.getAllCarConditions()

        assertEquals(2, result.size)
    }
}
