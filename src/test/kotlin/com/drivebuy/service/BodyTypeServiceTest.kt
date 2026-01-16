package com.drivebuy.service

import com.drivebuy.persistance.entity.car_info.BodyTypeEntity
import com.drivebuy.repository.car_info.BodyTypeRepository
import com.drivebuy.service.car_info.BodyTypeService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class BodyTypeServiceTest {

    private val bodyTypeRepository = mock(BodyTypeRepository::class.java)
    private val bodyTypeService = BodyTypeService(bodyTypeRepository)

    @Test
    fun `getBodyTypeById should return body type when found`() {
        val bodyType = BodyTypeEntity(id = 1, bodyTypeName = "Sedan")
        `when`(bodyTypeRepository.findById(1)).thenReturn(Optional.of(bodyType))

        val result = bodyTypeService.getBodyTypeById(1)

        assertNotNull(result)
        assertEquals("Sedan", result?.bodyTypeName)
    }

    @Test
    fun `getBodyTypeById should return null when not found`() {
        `when`(bodyTypeRepository.findById(1)).thenReturn(Optional.empty())

        val result = bodyTypeService.getBodyTypeById(1)

        assertNull(result)
    }

    @Test
    fun `getAllBodyTypes should return list of body types`() {
        val bodyTypes = listOf(
            BodyTypeEntity(id = 1, bodyTypeName = "Sedan"),
            BodyTypeEntity(id = 2, bodyTypeName = "SUV")
        )
        `when`(bodyTypeRepository.findAll()).thenReturn(bodyTypes)

        val result = bodyTypeService.getAllBodyTypes()

        assertEquals(2, result.size)
    }
}
