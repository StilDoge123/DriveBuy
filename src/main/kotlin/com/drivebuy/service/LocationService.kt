package com.drivebuy.service

import com.drivebuy.repository.CityRepository
import com.drivebuy.repository.CountryRepository
import com.drivebuy.repository.RegionRepository
import com.drivebuy.persistance.entity.City
import com.drivebuy.persistance.entity.Country
import com.drivebuy.persistance.entity.Region
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    fun getRegionById(id: Long): Region? {
        return regionRepository.findById(id).orElse(null)
    }

    fun getCityById(id: Long): City? {
        return cityRepository.findById(id).orElse(null)
    }

    fun createRegion(regionName: String): Region {
        val region = Region(regionName = regionName)
        return regionRepository.save(region)
    }

    fun updateRegion(id: Long, regionName: String): Region {
        val region = regionRepository.findById(id)
            .orElseThrow { NoSuchElementException("Region not found") }

        region.regionName = regionName
        return regionRepository.save(region)
    }

    @Transactional
    fun deleteRegion(id: Long) {
        val region = regionRepository.findById(id)
            .orElseThrow { NoSuchElementException("Region not found") }

        cityRepository.deleteAllByRegionId(id)
        regionRepository.delete(region)
    }

    fun createCity(cityName: String, regionId: Long): City {
        val region = regionRepository.findById(regionId)
            .orElse(null) ?: throw NoSuchElementException("Region with id $regionId not found")

        val city = City(cityName = cityName, region = region)
        return cityRepository.save(city)
    }

    fun addCitiesToRegion(regionId: Long, cityNames: List<String>): List<City> {
        val region = regionRepository.findById(regionId)
            .orElse(null) ?: throw NoSuchElementException("Region with id $regionId not found")

        val createdCities = mutableListOf<City>()

        for (cityName in cityNames) {
            val existingCity = cityRepository.findByRegionIdAndCityName(regionId, cityName)
            if (existingCity == null) {
                val city = City(cityName = cityName, region = region)
                createdCities.add(cityRepository.save(city))
            }
        }

        return createdCities
    }

    fun updateCity(id: Long, cityName: String, regionId: Long): City {
        val city = cityRepository.findById(id)
            .orElseThrow { NoSuchElementException("City not found") }

        val region = regionRepository.findById(regionId)
            .orElse(null) ?: throw NoSuchElementException("Region with id $regionId not found")

        city.cityName = cityName
        city.region = region
        return cityRepository.save(city)
    }

    fun deleteCity(id: Long) {
        val city = cityRepository.findById(id)
            .orElseThrow { NoSuchElementException("City not found") }

        cityRepository.delete(city)
    }
}