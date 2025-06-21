package com.drivebuy.service

import com.drivebuy.repository.CityRepository
import com.drivebuy.repository.CountryRepository
import com.drivebuy.repository.RegionRepository
import com.drivebuy.persistance.entity.City
import com.drivebuy.persistance.entity.Country
import com.drivebuy.persistance.entity.Region
import org.springframework.stereotype.Service

@Service
class LocationService(
    private val cityRepository: CityRepository,
    private val regionRepository: RegionRepository,
    private val countryRepository: CountryRepository
) {
    fun searchCities(query: String): List<City> {
        return cityRepository.findByCityNameContainingIgnoreCase(query)
    }

    fun searchRegions(query: String): List<Region> {
        return regionRepository.findByRegionNameContainingIgnoreCase(query)
    }

    fun searchCountries(query: String): List<Country> {
        return countryRepository.findByNameContainingIgnoreCase(query)
    }

    fun getAllRegions(): List<Region> {
        return regionRepository.findAll()
    }

    fun getCitiesByRegionId(regionId: Long): List<City> {
        return cityRepository.findAllByRegionId(regionId)
    }
}