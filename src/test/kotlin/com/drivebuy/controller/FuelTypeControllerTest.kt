package com.drivebuy.controller

import com.drivebuy.controller.car_info.FuelTypeController
import com.drivebuy.persistance.entity.car_info.FuelTypeEntity
import com.drivebuy.service.car_info.FuelTypeService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(FuelTypeController::class)
@AutoConfigureMockMvc(addFilters = false)
class FuelTypeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var fuelTypeService: FuelTypeService

    @Test
    fun `GET fuel types by id should return fuel type`() {
        val fuelType = FuelTypeEntity(id = 1, fuelTypeName = "Diesel")
        `when`(fuelTypeService.getFuelTypeById(1)).thenReturn(fuelType)

        mockMvc.perform(get("/fuelTypes/id/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.fuelTypeName").value("Diesel"))
    }

    @Test
    fun `GET all fuel types should return list`() {
        val fuelTypes = listOf(
            FuelTypeEntity(id = 1, fuelTypeName = "Diesel"),
            FuelTypeEntity(id = 2, fuelTypeName = "Petrol")
        )
        `when`(fuelTypeService.getAllFuelTypes()).thenReturn(fuelTypes)

        mockMvc.perform(get("/fuelTypes"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].fuelTypeName").value("Diesel"))
            .andExpect(jsonPath("$[1].fuelTypeName").value("Petrol"))
    }
}
