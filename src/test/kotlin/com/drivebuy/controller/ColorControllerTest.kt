package com.drivebuy.controller

import com.drivebuy.controller.car_info.ColorController
import com.drivebuy.persistance.entity.car_info.ColorEntity
import com.drivebuy.service.car_info.ColorService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(ColorController::class)
@AutoConfigureMockMvc(addFilters = false)
class ColorControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var colorService: ColorService

    @Test
    fun `GET colors by id should return color`() {
        val colorEntity = ColorEntity(id = 1, colorName = "Red")
        `when`(colorService.getColorById(1)).thenReturn(colorEntity)

        mockMvc.perform(get("/colors/id/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.colorName").value("Red"))
    }

    @Test
    fun `GET colors by name should return color`() {
        val colorEntity = ColorEntity(id = 1, colorName = "Blue")
        `when`(colorService.getColorByName("Blue")).thenReturn(colorEntity)

        mockMvc.perform(get("/colors/colorName/Blue"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.colorName").value("Blue"))
    }

    @Test
    fun `GET all colors should return list`() {
        val colors = listOf(
            ColorEntity(id = 1, colorName = "Red"),
            ColorEntity(id = 2, colorName = "Blue")
        )
        `when`(colorService.getAllColors()).thenReturn(colors)

        mockMvc.perform(get("/colors"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].colorName").value("Red"))
            .andExpect(jsonPath("$[1].colorName").value("Blue"))
    }
}
