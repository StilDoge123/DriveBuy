package com.drivebuy.controller

import com.drivebuy.controller.car_info.BodyTypeController
import com.drivebuy.persistance.entity.car_info.BodyTypeEntity
import com.drivebuy.service.car_info.BodyTypeService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(BodyTypeController::class)
@AutoConfigureMockMvc(addFilters = false)
class BodyTypeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var bodyTypeService: BodyTypeService

    @Test
    fun `GET body types by id should return body type`() {
        val bodyType = BodyTypeEntity(id = 1, bodyTypeName = "Sedan")
        `when`(bodyTypeService.getBodyTypeById(1)).thenReturn(bodyType)

        mockMvc.perform(get("/bodyTypes/id/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.bodyTypeName").value("Sedan"))
    }

    @Test
    fun `GET all body types should return list`() {
        val bodyTypes = listOf(
            BodyTypeEntity(id = 1, bodyTypeName = "Sedan"),
            BodyTypeEntity(id = 2, bodyTypeName = "SUV")
        )
        `when`(bodyTypeService.getAllBodyTypes()).thenReturn(bodyTypes)

        mockMvc.perform(get("/bodyTypes"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].bodyTypeName").value("Sedan"))
            .andExpect(jsonPath("$[1].bodyTypeName").value("SUV"))
    }
}
