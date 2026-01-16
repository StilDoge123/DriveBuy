package com.drivebuy.controller

import com.drivebuy.persistance.entity.CarBrandEntity
import com.drivebuy.persistance.entity.CarModelEntity
import com.drivebuy.service.CarModelService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(CarModelController::class)
@AutoConfigureMockMvc(addFilters = false)
class CarModelControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var carModelService: CarModelService

    @Test
    fun `GET model by id should return model`() {
        val brand = CarBrandEntity(id = 1, brandName = "Toyota")
        val model = CarModelEntity(id = 1, brand = brand, modelName = "Camry")
        `when`(carModelService.getModelById(1)).thenReturn(model)

        mockMvc.perform(get("/models/id/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.modelName").value("Camry"))
    }

    @Test
    fun `GET models by brand id should return list`() {
        val brand = CarBrandEntity(id = 1, brandName = "Toyota")
        val models = listOf(
            CarModelEntity(id = 1, brand = brand, modelName = "Camry"),
            CarModelEntity(id = 2, brand = brand, modelName = "Corolla")
        )
        `when`(carModelService.getAllModelsByBrandId(1)).thenReturn(models)

        mockMvc.perform(get("/models/brandId/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].modelName").value("Camry"))
            .andExpect(jsonPath("$[1].modelName").value("Corolla"))
    }
}
