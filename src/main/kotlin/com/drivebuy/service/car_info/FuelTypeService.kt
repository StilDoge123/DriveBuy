package com.drivebuy.service.car_info

import com.drivebuy.persistance.entity.car_info.FuelTypeEntity
import com.drivebuy.repository.car_info.FuelTypeRepository
import org.springframework.stereotype.Service


@Service
class FuelTypeService(private val fuelTypeRepository: FuelTypeRepository) {
    fun getFuelTypeById(id: Long): FuelTypeEntity? {
        return fuelTypeRepository.findById(id).orElse(null)
    }

    fun getFuelTypeByTypeName(fuelTypeName: String): FuelTypeEntity? {
        return fuelTypeRepository.findByFuelTypeName(fuelTypeName)
    }

    fun getAllFuelTypes(): List<FuelTypeEntity?> {
        return fuelTypeRepository.findAll()
    }
}