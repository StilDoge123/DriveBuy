package com.drivebuy.service.car_info

import com.drivebuy.persistance.entity.car_info.CarFeaturesEntity
import com.drivebuy.repository.car_info.CarFeaturesRepository
import org.springframework.stereotype.Service


@Service
class CarFeaturesService(private val carFeaturesRepository: CarFeaturesRepository) {
    fun getCarFeatureById(id: Long): CarFeaturesEntity? {
        return carFeaturesRepository.findById(id).orElse(null)
    }

    fun getCarConditionByConditionName(carFeatureName: String): CarFeaturesEntity? {
        return carFeaturesRepository.findByFeatureName(carFeatureName)
    }

    fun getAllCarFeatures(): List<CarFeaturesEntity?> {
        return carFeaturesRepository.findAll()
    }
}