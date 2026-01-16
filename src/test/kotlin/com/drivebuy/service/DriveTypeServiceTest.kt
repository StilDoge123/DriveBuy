package com.drivebuy.service

import com.drivebuy.persistance.entity.car_info.DriveTypeEntity
import com.drivebuy.repository.car_info.DriveTypeRepository
import com.drivebuy.service.car_info.DriveTypeService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class DriveTypeServiceTest {

    private val driveTypeRepository = mock(DriveTypeRepository::class.java)
    private val driveTypeService = DriveTypeService(driveTypeRepository)

    @Test
    fun `getDriveTypeById should return drive type when found`() {
        val driveType = DriveTypeEntity(id = 1, driveTypeName = "AWD")
        `when`(driveTypeRepository.findById(1)).thenReturn(Optional.of(driveType))

        val result = driveTypeService.getDriveTypeById(1)

        assertNotNull(result)
        assertEquals("AWD", result?.driveTypeName)
    }

    @Test
    fun `getDriveTypeById should return null when not found`() {
        `when`(driveTypeRepository.findById(1)).thenReturn(Optional.empty())

        val result = driveTypeService.getDriveTypeById(1)

        assertNull(result)
    }

    @Test
    fun `getAllDriveTypes should return list`() {
        val driveTypes = listOf(
            DriveTypeEntity(id = 1, driveTypeName = "AWD"),
            DriveTypeEntity(id = 2, driveTypeName = "FWD")
        )
        `when`(driveTypeRepository.findAll()).thenReturn(driveTypes)

        val result = driveTypeService.getAllDriveTypes()

        assertEquals(2, result.size)
    }
}
