package com.drivebuy.controller

import com.drivebuy.persistance.entity.City
import com.drivebuy.persistance.entity.Country
import com.drivebuy.persistance.entity.Region
import com.drivebuy.security.RequiresApiKey
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

    @RequiresApiKey
    @PostMapping("/regions/create")
    fun createRegion(@RequestParam regionName: String): Region {
        return locationService.createRegion(regionName)
    }

    @RequiresApiKey
    @PutMapping("/regions/update/{regionId}")
    fun updateRegion(
        @PathVariable regionId: Long,
        @RequestParam regionName: String): Region {
        return locationService.updateRegion(regionId, regionName)
    }

    @RequiresApiKey
    @DeleteMapping("/regions/delete/{regionId}")
    fun deleteRegion(@PathVariable regionId: Long) {
        locationService.deleteRegion(regionId)
    }

    @RequiresApiKey
    @PostMapping("/cities/create")
    fun createCity(
        @RequestParam cityName: String,
        @RequestParam regionId: Long): City {
        return locationService.createCity(cityName, regionId)
    }

    @RequiresApiKey
    @PostMapping("/cities/addCities/{regionId}")
    fun addCitiesToRegion(
        @PathVariable regionId: Long,
        @RequestBody cityNames: List<String>): List<City> {
        return locationService.addCitiesToRegion(regionId, cityNames)
    }

    @RequiresApiKey
    @PutMapping("/cities/update/{cityId}")
    fun updateCity(
        @PathVariable cityId: Long,
        @RequestParam cityName: String,
        @RequestParam regionId: Long): City {
        return locationService.updateCity(cityId, cityName, regionId)
    }

    @RequiresApiKey
    @DeleteMapping("/cities/delete/{cityId}")
    fun deleteCity(@PathVariable cityId: Long) {
        locationService.deleteCity(cityId)
    }
}