package com.drivebuy.controller

import com.drivebuy.persistance.entity.CarBrandEntity
import com.drivebuy.service.CarBrandService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(CarBrandController::class)
@AutoConfigureMockMvc(addFilters = false)
class CarBrandControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var carBrandService: CarBrandService

    @Test
    fun `GET brand by id should return brand`() {
        val brand = CarBrandEntity(id = 1, brandName = "Toyota")
        `when`(carBrandService.getBrandById(1)).thenReturn(brand)

        mockMvc.perform(get("/brands/id/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.brandName").value("Toyota"))
    }

    @Test
    fun `GET brand by name should return brand`() {
        val brand = CarBrandEntity(id = 1, brandName = "Honda")
        `when`(carBrandService.getBrandByName("Honda")).thenReturn(brand)

        mockMvc.perform(get("/brands/brandName/Honda"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.brandName").value("Honda"))
    }

    @Test
    fun `GET all brands should return list`() {
        val brands = listOf(
            CarBrandEntity(id = 1, brandName = "Toyota"),
            CarBrandEntity(id = 2, brandName = "Honda")
        )
        `when`(carBrandService.getAllBrands()).thenReturn(brands)

        mockMvc.perform(get("/brands"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].brandName").value("Toyota"))
            .andExpect(jsonPath("$[1].brandName").value("Honda"))
    }
}
