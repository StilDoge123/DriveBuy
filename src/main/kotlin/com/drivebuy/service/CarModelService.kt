package com.drivebuy.service

import com.drivebuy.persistance.entity.CarModelEntity
import com.drivebuy.repository.CarModelRepository
import org.springframework.stereotype.Service

@Service
class CarModelService(private val carModelRepository: CarModelRepository) {
    fun getModelById(id: Long): CarModelEntity? {
        return carModelRepository.findById(id).orElse(null)
    }

    fun getAllModelsByBrandId(brandId: Long): List<CarModelEntity?> {
        return carModelRepository.findAllByBrandId(brandId)
    }
}