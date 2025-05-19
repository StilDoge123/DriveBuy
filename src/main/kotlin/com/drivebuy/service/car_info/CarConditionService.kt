package com.drivebuy.service.car_info

import com.drivebuy.persistance.entity.car_info.CarConditionEntity
import com.drivebuy.repository.car_info.CarConditionRepository
import org.springframework.stereotype.Service

@Service
class CarConditionService(private val carConditionRepository: CarConditionRepository) {
    fun getCarConditionById(id: Long): CarConditionEntity? {
        return carConditionRepository.findById(id).orElse(null)
    }

    fun getCarConditionByConditionName(carConditionName: String): CarConditionEntity? {
        return carConditionRepository.findByConditionName(carConditionName)
    }

    fun getAllCarConditions(): List<CarConditionEntity?> {
        return carConditionRepository.findAll()
    }
}