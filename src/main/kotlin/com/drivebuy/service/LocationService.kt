package com.drivebuy.service

import com.drivebuy.persistance.CityRepository
import com.drivebuy.persistance.CountryRepository
import com.drivebuy.persistance.RegionRepository
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
        return cityRepository.findByNameContainingIgnoreCase(query)
    }

    fun searchRegions(query: String): List<Region> {
        return regionRepository.findByNameContainingIgnoreCase(query)
    }

    fun searchCountries(query: String): List<Country> {
        return countryRepository.findByNameContainingIgnoreCase(query)
    }
}