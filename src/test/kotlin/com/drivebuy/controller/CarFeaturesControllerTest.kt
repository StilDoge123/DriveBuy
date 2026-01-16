package com.drivebuy.controller

import com.drivebuy.controller.car_info.CarFeaturesController
import com.drivebuy.persistance.entity.car_info.CarFeaturesEntity
import com.drivebuy.service.car_info.CarFeaturesService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(CarFeaturesController::class)
@AutoConfigureMockMvc(addFilters = false)
class CarFeaturesControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var carFeaturesService: CarFeaturesService

    @Test
    fun `GET car feature by id should return car feature`() {
        val feature = CarFeaturesEntity(id = 1, featureName = "Sunroof")
        `when`(carFeaturesService.getCarFeatureById(1)).thenReturn(feature)

        mockMvc.perform(get("/carFeatures/id/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.featureName").value("Sunroof"))
    }

    @Test
    fun `GET all car features should return list`() {
        val features = listOf(
            CarFeaturesEntity(id = 1, featureName = "Sunroof"),
            CarFeaturesEntity(id = 2, featureName = "GPS")
        )
        `when`(carFeaturesService.getAllCarFeatures()).thenReturn(features)

        mockMvc.perform(get("/carFeatures"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].featureName").value("Sunroof"))
            .andExpect(jsonPath("$[1].featureName").value("GPS"))
    }
}
