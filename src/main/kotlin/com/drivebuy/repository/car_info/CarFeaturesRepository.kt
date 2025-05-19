package com.drivebuy.repository.car_info

import com.drivebuy.persistance.entity.car_info.CarFeaturesEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CarFeaturesRepository : JpaRepository<CarFeaturesEntity, Long> {
    fun findByFeatureName(featureName: String): CarFeaturesEntity?
}