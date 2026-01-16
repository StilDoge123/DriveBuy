package com.drivebuy.service

import com.drivebuy.persistance.entity.City
import com.drivebuy.persistance.entity.Country
import com.drivebuy.persistance.entity.Region
import com.drivebuy.repository.CityRepository
import com.drivebuy.repository.CountryRepository
import com.drivebuy.repository.RegionRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class LocationServiceTest {

    private val cityRepository = mock(CityRepository::class.java)
    private val regionRepository = mock(RegionRepository::class.java)
    private val countryRepository = mock(CountryRepository::class.java)
    private val locationService = LocationService(cityRepository, regionRepository, countryRepository)

    @Test
    fun `searchCities should return cities matching query`() {
        val region = Region(id = 1, regionName = "Sofia")
        val cities = listOf(City(id = 1, region = region, cityName = "Sofia"))
        `when`(cityRepository.findByCityNameContainingIgnoreCase("Sofia")).thenReturn(cities)

        val result = locationService.searchCities("Sofia")

        assertEquals(1, result.size)
        assertEquals("Sofia", result[0].cityName)
    }

    @Test
    fun `searchRegions should return regions matching query`() {
        val regions = listOf(Region(id = 1, regionName = "Sofia"))
        `when`(regionRepository.findByRegionNameContainingIgnoreCase("Sofia")).thenReturn(regions)

        val result = locationService.searchRegions("Sofia")

        assertEquals(1, result.size)
        assertEquals("Sofia", result[0].regionName)
    }

    @Test
    fun `getAllRegions should return all regions`() {
        val regions = listOf(
            Region(id = 1, regionName = "Sofia"),
            Region(id = 2, regionName = "Plovdiv")
        )
        `when`(regionRepository.findAll()).thenReturn(regions)

        val result = locationService.getAllRegions()

        assertEquals(2, result.size)
    }

    @Test
    fun `getCitiesByRegionId should return cities for region`() {
        val region = Region(id = 1, regionName = "Sofia")
        val cities = listOf(City(id = 1, region = region, cityName = "Sofia"))
        `when`(cityRepository.findAllByRegionId(1)).thenReturn(cities)

        val result = locationService.getCitiesByRegionId(1)

        assertEquals(1, result.size)
        assertEquals("Sofia", result[0].cityName)
    }
}
