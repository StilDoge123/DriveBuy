package com.drivebuy.repository

import com.drivebuy.persistance.entity.City
import com.drivebuy.persistance.entity.Country
import com.drivebuy.persistance.entity.Region
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : JpaRepository<Country, Long> {
    fun findByNameContainingIgnoreCase(name: String): List<Country>
}

@Repository
interface RegionRepository : JpaRepository<Region, Long> {
    fun findByRegionNameContainingIgnoreCase(regionName: String): List<Region>
    fun findByRegionName(regionName: String): Region?
}

@Repository
interface CityRepository : JpaRepository<City, Long> {
    fun findByCityNameContainingIgnoreCase(cityName: String): List<City>
    fun findAllByRegionId(regionId: Long): List<City>
    fun deleteAllByRegionId(regionId: Long)
}