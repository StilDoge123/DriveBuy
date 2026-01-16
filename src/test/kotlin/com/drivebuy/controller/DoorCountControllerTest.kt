package com.drivebuy.controller

import com.drivebuy.controller.car_info.DoorCountController
import com.drivebuy.persistance.entity.car_info.DoorCountEntity
import com.drivebuy.service.car_info.DoorCountService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(DoorCountController::class)
@AutoConfigureMockMvc(addFilters = false)
class DoorCountControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var doorCountService: DoorCountService

    @Test
    fun `GET door count by id should return door count`() {
        val doorCount = DoorCountEntity(id = 1, doorCount = "4")
        `when`(doorCountService.getDoorCountById(1)).thenReturn(doorCount)

        mockMvc.perform(get("/doorCounts/id/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.doorCount").value("4"))
    }

    @Test
    fun `GET all door counts should return list`() {
        val doorCounts = listOf(
            DoorCountEntity(id = 1, doorCount = "2"),
            DoorCountEntity(id = 2, doorCount = "4")
        )
        `when`(doorCountService.getAllDoorCounts()).thenReturn(doorCounts)

        mockMvc.perform(get("/doorCounts"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].doorCount").value("2"))
            .andExpect(jsonPath("$[1].doorCount").value("4"))
    }
}
