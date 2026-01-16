package com.drivebuy.controller

import com.drivebuy.controller.car_info.CylinderCountController
import com.drivebuy.persistance.entity.car_info.CylinderCountEntity
import com.drivebuy.service.car_info.CylinderCountService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(CylinderCountController::class)
@AutoConfigureMockMvc(addFilters = false)
class CylinderCountControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var cylinderCountService: CylinderCountService

    @Test
    fun `GET cylinder count by id should return cylinder count`() {
        val cylinderCount = CylinderCountEntity(id = 1, cylinderCount = "4")
        `when`(cylinderCountService.getCylinderCountById(1)).thenReturn(cylinderCount)

        mockMvc.perform(get("/cylinderCounts/id/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.cylinderCount").value("4"))
    }

    @Test
    fun `GET all cylinder counts should return list`() {
        val cylinderCounts = listOf(
            CylinderCountEntity(id = 1, cylinderCount = "4"),
            CylinderCountEntity(id = 2, cylinderCount = "6")
        )
        `when`(cylinderCountService.getAllCylinderCounts()).thenReturn(cylinderCounts)

        mockMvc.perform(get("/cylinderCounts"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].cylinderCount").value("4"))
            .andExpect(jsonPath("$[1].cylinderCount").value("6"))
    }
}
