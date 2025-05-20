package com.drivebuy.db_initialiser

import com.drivebuy.persistance.entity.CarBrandEntity
import com.drivebuy.persistance.entity.CarModelEntity
import com.drivebuy.persistance.enums.CarBrand
import com.drivebuy.repository.CarBrandRepository
import com.drivebuy.repository.CarModelRepository
import jakarta.annotation.PostConstruct
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate


@Component
class CarBrandInitializer(
    private val brandRepository: CarBrandRepository,
    private val modelRepository: CarModelRepository,
    private val transactionTemplate: TransactionTemplate
) {
    @PostConstruct
    fun init() {
        transactionTemplate.execute {
            initializeBrandsAndModels()
        }
    }

    @Transactional
    private fun initializeBrandsAndModels() {
        val enumBrandNames = CarBrand.entries.map { it.brandName }.toSet()
        val repoBrands = brandRepository.findAll()

        // Delete brands that exist in repo but not in enum
        repoBrands.forEach { repoBrand ->
            if (!enumBrandNames.contains(repoBrand.brandName)) {
                // Delete within the same transaction
                modelRepository.deleteAllByBrandId(repoBrand.id)
                brandRepository.delete(repoBrand)
            }
        }

        CarBrand.entries.forEach { carBrand ->
            var brand = brandRepository.findByBrandName(carBrand.brandName)
                ?: brandRepository.save(CarBrandEntity(brandName = carBrand.brandName))

            if (brand.brandName != carBrand.brandName) {
                brand = brand.copy(brandName = carBrand.brandName)
                brandRepository.save(brand)
            }

            val existingModels = modelRepository.findAllByBrandId(brand.id).map { it.modelName }
            val newModels = carBrand.models
                .filterNot { existingModels.contains(it) }
                .map { CarModelEntity(modelName = it, brand = brand) }

            modelRepository.saveAll(newModels)
        }
    }
}
