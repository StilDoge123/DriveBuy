package com.drivebuy.db_initialiser

import com.drivebuy.persistance.entity.CarBrandEntity
import com.drivebuy.persistance.entity.CarModelEntity
import com.drivebuy.persistance.enums.CarBrand
import com.drivebuy.repository.CarBrandRepository
import com.drivebuy.repository.CarModelRepository
import jakarta.annotation.PostConstruct
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class CarBrandInitializer(
    private val brandRepository: CarBrandRepository,
    private val modelRepository: CarModelRepository
) {
    @PostConstruct
    @Transactional
    fun init() {
        CarBrand.entries.forEach { carBrand ->
            var brand = brandRepository.findByName(carBrand.brandName)
                ?: brandRepository.save(CarBrandEntity(name = carBrand.name))

            // Update brand name if changed in the enum
            if (brand.name != carBrand.brandName) {
                brand = brand.copy(name = carBrand.brandName)
                brandRepository.save(brand)
            }

            // Get existing model names for this brand
            val existingModels = modelRepository.findAllByBrandId(brand.id).map { it.modelName }

            // Add new models from the enum that don't exist yet
            val newModels = carBrand.models
                .filterNot { existingModels.contains(it) }
                .map { CarModelEntity(modelName = it, brand = brand) }

            modelRepository.saveAll(newModels)
        }
    }
}
