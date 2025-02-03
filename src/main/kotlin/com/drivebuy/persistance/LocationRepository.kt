package com.drivebuy.persistance

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
    fun findByNameContainingIgnoreCase(name: String): List<Region>
}

@Repository
interface CityRepository : JpaRepository<City, Long> {
    fun findByNameContainingIgnoreCase(name: String): List<City>
}