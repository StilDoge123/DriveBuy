package com.drivebuy.controller

import com.drivebuy.controller.car_info.TransmissionTypeController
import com.drivebuy.persistance.entity.car_info.TransmissionTypeEntity
import com.drivebuy.service.car_info.TransmissionTypeService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(TransmissionTypeController::class)
@AutoConfigureMockMvc(addFilters = false)
class TransmissionTypeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var transmissionTypeService: TransmissionTypeService

    @Test
    fun `GET transmission type by id should return transmission type`() {
        val transmission = TransmissionTypeEntity(id = 1, transmissionTypeName = "Manual")
        `when`(transmissionTypeService.getTransmissionTypeById(1)).thenReturn(transmission)

        mockMvc.perform(get("/transmissionTypes/id/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.transmissionTypeName").value("Manual"))
    }

    @Test
    fun `GET all transmission types should return list`() {
        val transmissions = listOf(
            TransmissionTypeEntity(id = 1, transmissionTypeName = "Manual"),
            TransmissionTypeEntity(id = 2, transmissionTypeName = "Automatic")
        )
        `when`(transmissionTypeService.getAllTransmissionTypes()).thenReturn(transmissions)

        mockMvc.perform(get("/transmissionTypes"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].transmissionTypeName").value("Manual"))
            .andExpect(jsonPath("$[1].transmissionTypeName").value("Automatic"))
    }
}
