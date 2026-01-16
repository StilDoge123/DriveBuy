package com.drivebuy.controller

import com.drivebuy.controller.car_info.CarConditionController
import com.drivebuy.persistance.entity.car_info.CarConditionEntity
import com.drivebuy.service.car_info.CarConditionService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(CarConditionController::class)
@AutoConfigureMockMvc(addFilters = false)
class CarConditionControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var carConditionService: CarConditionService

    @Test
    fun `GET car condition by id should return car condition`() {
        val condition = CarConditionEntity(id = 1, conditionName = "New")
        `when`(carConditionService.getCarConditionById(1)).thenReturn(condition)

        mockMvc.perform(get("/carConditions/id/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.conditionName").value("New"))
    }

    @Test
    fun `GET all car conditions should return list`() {
        val conditions = listOf(
            CarConditionEntity(id = 1, conditionName = "New"),
            CarConditionEntity(id = 2, conditionName = "Used")
        )
        `when`(carConditionService.getAllCarConditions()).thenReturn(conditions)

        mockMvc.perform(get("/carConditions"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].conditionName").value("New"))
            .andExpect(jsonPath("$[1].conditionName").value("Used"))
    }
}
