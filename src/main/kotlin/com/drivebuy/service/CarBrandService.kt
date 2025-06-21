package com.drivebuy.service

import com.drivebuy.persistance.entity.CarBrandEntity
import com.drivebuy.repository.CarBrandRepository
import org.springframework.stereotype.Service

@Service
class CarBrandService(private val carBrandRepository: CarBrandRepository) {
    fun getBrandById(id: Long): CarBrandEntity? {
        return carBrandRepository.findById(id).orElse(null)
    }

    fun getBrandByName(brandName: String): CarBrandEntity? {
        return carBrandRepository.findByBrandName(brandName)
    }

    fun getAllBrands(): List<CarBrandEntity?> {
        return carBrandRepository.findAll().filterNotNull().sortedBy { it.brandName.lowercase() }
    }
}