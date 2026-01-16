package com.drivebuy.service

import com.drivebuy.persistance.entity.CarBrandEntity
import com.drivebuy.repository.CarBrandRepository
import com.drivebuy.repository.CarModelRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CarBrandService(
    private val carBrandRepository: CarBrandRepository,
    private val carModelRepository: CarModelRepository
) {
    fun getBrandById(id: Long): CarBrandEntity? {
        return carBrandRepository.findById(id).orElse(null)
    }

    fun getBrandByName(brandName: String): CarBrandEntity? {
        return carBrandRepository.findByBrandName(brandName)
    }

    fun getAllBrands(): List<CarBrandEntity?> {
        return carBrandRepository.findAll().filterNotNull().sortedBy { it.brandName.lowercase() }
    }

    fun createCarBrand(brandName: String): CarBrandEntity {
        val carBrand = CarBrandEntity(brandName = brandName)
        return carBrandRepository.save(carBrand)
    }

    fun updateCarBrand(id: Long, brandName: String): CarBrandEntity {
        val carBrand = carBrandRepository.findById(id)
            .orElseThrow { NoSuchElementException("Car brand not found") }

        carBrand.brandName = brandName
        return carBrandRepository.save(carBrand)
    }

    @Transactional
    fun deleteCarBrand(id: Long) {
        val carBrand = carBrandRepository.findById(id)
            .orElseThrow { NoSuchElementException("Car brand not found") }

        carModelRepository.deleteAllByBrandId(id)
        carBrandRepository.delete(carBrand)
    }
}