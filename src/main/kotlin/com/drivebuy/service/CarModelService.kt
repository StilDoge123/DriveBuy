package com.drivebuy.service

import com.drivebuy.persistance.entity.CarModelEntity
import com.drivebuy.repository.CarModelRepository
import org.springframework.stereotype.Service

@Service
class CarModelService(
    private val carModelRepository: CarModelRepository,
    private val carBrandService: CarBrandService
) {
    fun getModelById(id: Long): CarModelEntity? {
        return carModelRepository.findById(id).orElse(null)
    }

    fun getAllModelsByBrandId(brandId: Long): List<CarModelEntity?> {
        return carModelRepository.findAllByBrandId(brandId)
    }

    fun createCarModel(modelName: String, brandId: Long): CarModelEntity {
        val brand = carBrandService.getBrandById(brandId)
            ?: throw NoSuchElementException("Car brand with id $brandId not found")

        val carModel = CarModelEntity(
            modelName = modelName,
            brand = brand
        )
        return carModelRepository.save(carModel);
    }

    fun addModelsToBrand(brandId: Long, modelNames: List<String>): List<CarModelEntity> {
        val brand = carBrandService.getBrandById(brandId)
            ?: throw NoSuchElementException("Car brand with id $brandId not found")

        val createdModels = mutableListOf<CarModelEntity>()

        for (modelName in modelNames) {
            val existingModel = carModelRepository.findByBrandIdAndModelName(brandId, modelName)
            if (existingModel == null) {
                val carModel = CarModelEntity(
                    modelName = modelName,
                    brand = brand
                )
                createdModels.add(carModelRepository.save(carModel))
            }
        }

        return createdModels
    }

    fun updateCarModel(id: Long, modelName: String, brandId: Long): CarModelEntity {
        val carModel = carModelRepository.findById(id)
            .orElseThrow { NoSuchElementException("Car model not found") }

        val brand = carBrandService.getBrandById(brandId)
            ?: throw NoSuchElementException("Car brand with id $brandId not found")

        carModel.modelName = modelName
        carModel.brand = brand
        return carModelRepository.save(carModel)
    }

    fun deleteCarModel(id: Long) {
        val carModel = carModelRepository.findById(id)
            .orElseThrow { NoSuchElementException("Car model not found") }

        carModelRepository.delete(carModel)
    }
}