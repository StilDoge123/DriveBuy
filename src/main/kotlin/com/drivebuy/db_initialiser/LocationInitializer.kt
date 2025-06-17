package com.drivebuy.db_initialiser

import com.drivebuy.persistance.entity.City
import com.drivebuy.persistance.entity.Region
import com.drivebuy.persistance.enums.RegionsWithCities
import com.drivebuy.repository.CityRepository
import com.drivebuy.repository.RegionRepository
import jakarta.annotation.PostConstruct
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate

@Component
class LocationInitializer(
    private val regionRepository: RegionRepository,
    private val cityRepository: CityRepository,
    private val transactionTemplate: TransactionTemplate
) {
    @PostConstruct
    fun init() {
        transactionTemplate.executeWithoutResult {
            initializeRegionsAndCities()
        }
    }

    @Transactional
    private fun initializeRegionsAndCities() {
        val enumRegionNames = RegionsWithCities.entries.map { it.regionName }.toSet()

        // Delete obsolete regions
        regionRepository.findAll().forEach { repoRegion ->
            if (!enumRegionNames.contains(repoRegion.regionName)) {
                repoRegion.id?.let { cityRepository.deleteAllByRegionId(it) }
                regionRepository.delete(repoRegion)
            }
        }

        // Process all enum entries
        RegionsWithCities.entries.forEach { regionWithCities ->
            val region = regionRepository.findByRegionName(regionWithCities.regionName)
                ?: regionRepository.save(Region(regionName = regionWithCities.regionName))

            if (region.regionName != regionWithCities.regionName) {
                regionRepository.save(region.copy(regionName = regionWithCities.regionName))
            }

            val existingCities = region.id?.let {
                cityRepository.findAllByRegionId(it).map { it.cityName }
            } ?: emptyList()

            val newCities = regionWithCities.cities
                .filterNot { it in existingCities }
                .map { City(cityName = it, region = region) }

            if (newCities.isNotEmpty()) {
                cityRepository.saveAll(newCities)
            }
        }
    }
}
