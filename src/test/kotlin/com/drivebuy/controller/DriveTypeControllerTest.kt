package com.drivebuy.controller

import com.drivebuy.controller.car_info.DriveTypeController
import com.drivebuy.persistance.entity.car_info.DriveTypeEntity
import com.drivebuy.service.car_info.DriveTypeService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(DriveTypeController::class)
@AutoConfigureMockMvc(addFilters = false)
class DriveTypeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var driveTypeService: DriveTypeService

    @Test
    fun `GET drive type by id should return drive type`() {
        val driveType = DriveTypeEntity(id = 1, driveTypeName = "AWD")
        `when`(driveTypeService.getDriveTypeById(1)).thenReturn(driveType)

        mockMvc.perform(get("/driveTypes/id/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.driveTypeName").value("AWD"))
    }

    @Test
    fun `GET all drive types should return list`() {
        val driveTypes = listOf(
            DriveTypeEntity(id = 1, driveTypeName = "AWD"),
            DriveTypeEntity(id = 2, driveTypeName = "FWD")
        )
        `when`(driveTypeService.getAllDriveTypes()).thenReturn(driveTypes)

        mockMvc.perform(get("/driveTypes"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].driveTypeName").value("AWD"))
            .andExpect(jsonPath("$[1].driveTypeName").value("FWD"))
    }
}
