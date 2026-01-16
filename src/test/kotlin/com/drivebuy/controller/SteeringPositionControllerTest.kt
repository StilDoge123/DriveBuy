package com.drivebuy.controller

import com.drivebuy.controller.car_info.SteeringPositionTypes
import com.drivebuy.persistance.entity.car_info.SteeringPositionEntity
import com.drivebuy.service.car_info.SteeringPositionService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(SteeringPositionTypes::class)
@AutoConfigureMockMvc(addFilters = false)
class SteeringPositionControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var steeringPositionService: SteeringPositionService

    @Test
    fun `GET steering position by id should return steering position`() {
        val steeringPosition = SteeringPositionEntity(id = 1, steeringPositionName = "Left")
        `when`(steeringPositionService.getSteeringPositionById(1)).thenReturn(steeringPosition)

        mockMvc.perform(get("/steeringPositions/id/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.steeringPositionName").value("Left"))
    }

    @Test
    fun `GET all steering positions should return list`() {
        val steeringPositions = listOf(
            SteeringPositionEntity(id = 1, steeringPositionName = "Left"),
            SteeringPositionEntity(id = 2, steeringPositionName = "Right")
        )
        `when`(steeringPositionService.getAllSteeringPositions()).thenReturn(steeringPositions)

        mockMvc.perform(get("/steeringPositions"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].steeringPositionName").value("Left"))
            .andExpect(jsonPath("$[1].steeringPositionName").value("Right"))
    }
}
