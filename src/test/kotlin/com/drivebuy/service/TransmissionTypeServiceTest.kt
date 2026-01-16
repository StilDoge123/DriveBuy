package com.drivebuy.service

import com.drivebuy.persistance.entity.car_info.TransmissionTypeEntity
import com.drivebuy.repository.car_info.TransmissionTypeRepository
import com.drivebuy.service.car_info.TransmissionTypeService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class TransmissionTypeServiceTest {

    private val transmissionTypeRepository = mock(TransmissionTypeRepository::class.java)
    private val transmissionTypeService = TransmissionTypeService(transmissionTypeRepository)

    @Test
    fun `getTransmissionTypeById should return transmission type when found`() {
        val transmission = TransmissionTypeEntity(id = 1, transmissionTypeName = "Manual")
        `when`(transmissionTypeRepository.findById(1)).thenReturn(Optional.of(transmission))

        val result = transmissionTypeService.getTransmissionTypeById(1)

        assertNotNull(result)
        assertEquals("Manual", result?.transmissionTypeName)
    }

    @Test
    fun `getTransmissionTypeById should return null when not found`() {
        `when`(transmissionTypeRepository.findById(1)).thenReturn(Optional.empty())

        val result = transmissionTypeService.getTransmissionTypeById(1)

        assertNull(result)
    }

    @Test
    fun `getAllTransmissionTypes should return list`() {
        val transmissions = listOf(
            TransmissionTypeEntity(id = 1, transmissionTypeName = "Manual"),
            TransmissionTypeEntity(id = 2, transmissionTypeName = "Automatic")
        )
        `when`(transmissionTypeRepository.findAll()).thenReturn(transmissions)

        val result = transmissionTypeService.getAllTransmissionTypes()

        assertEquals(2, result.size)
    }
}
