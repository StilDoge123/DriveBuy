package com.drivebuy.controller

import com.drivebuy.persistance.entity.City
import com.drivebuy.persistance.entity.Region
import com.drivebuy.service.LocationService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(LocationController::class)
@AutoConfigureMockMvc(addFilters = false)
class LocationControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var locationService: LocationService

    @Test
    fun `GET search cities should return matching cities`() {
        val region = Region(id = 1, regionName = "Sofia")
        val cities = listOf(City(id = 1, region = region, cityName = "Sofia"))
        `when`(locationService.searchCities("Sofia")).thenReturn(cities)

        mockMvc.perform(get("/locations/search/cities?query=Sofia"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].cityName").value("Sofia"))
    }

    @Test
    fun `GET all regions should return list`() {
        val regions = listOf(
            Region(id = 1, regionName = "Sofia"),
            Region(id = 2, regionName = "Plovdiv")
        )
        `when`(locationService.getAllRegions()).thenReturn(regions)

        mockMvc.perform(get("/locations/regions"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].regionName").value("Sofia"))
            .andExpect(jsonPath("$[1].regionName").value("Plovdiv"))
    }

    @Test
    fun `GET cities by region should return cities for that region`() {
        val region = Region(id = 1, regionName = "Sofia")
        val cities = listOf(City(id = 1, region = region, cityName = "Sofia"))
        `when`(locationService.getCitiesByRegionId(1)).thenReturn(cities)

        mockMvc.perform(get("/locations/regions/1/cities"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].cityName").value("Sofia"))
    }
}
