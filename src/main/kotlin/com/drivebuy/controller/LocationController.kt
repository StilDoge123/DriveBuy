package com.drivebuy.controller

import com.drivebuy.persistance.entity.City
import com.drivebuy.persistance.entity.Country
import com.drivebuy.persistance.entity.Region
import com.drivebuy.service.LocationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/locations")
class LocationController(private val locationService: LocationService) {
    @GetMapping("/search/cities")
    fun searchCities(@RequestParam query: String): List<City> {
        return locationService.searchCities(query)
    }

    @GetMapping("/search/regions")
    fun searchRegions(@RequestParam query: String): List<Region> {
        return locationService.searchRegions(query)
    }

    @GetMapping("/search/countries")
    fun searchCountries(@RequestParam query: String): List<Country> {
        return locationService.searchCountries(query)
    }

    @GetMapping("/regions")
    fun getAllRegions(): List<Region> {
        return locationService.getAllRegions()
    }

    @GetMapping("/regions/{regionId}/cities")
    fun getCitiesByRegion(@PathVariable regionId: Long): List<City> {
        return locationService.getCitiesByRegionId(regionId)
    }
}