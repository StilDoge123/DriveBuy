package com.drivebuy.service

import com.drivebuy.persistance.dto.CarAdFilters
import com.drivebuy.repository.AdRepository
import com.drivebuy.persistance.entity.CarAdEntity
import com.drivebuy.persistance.request.CreateAdRequest
import com.drivebuy.persistance.request.UpdateAdRequest
import com.drivebuy.repository.UserRepository
import com.drivebuy.repository.car_info.CarFeaturesRepository
import com.drivebuy.repository.specifications.CarAdSpecifications
import jakarta.transaction.Transactional
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AdService(
    private val adRepository: AdRepository,
    private val userRepository: UserRepository,
    private val featureRepository: CarFeaturesRepository,
    private val storageService: FirebaseStorageService
) {
    @Transactional
    fun createAd(request: CreateAdRequest): CarAdEntity {
        val user = userRepository.findById(request.userId)
            .orElseThrow { RuntimeException("User not found. Register or login to create an ad.") }

        val imageUrls = request.images.map { storageService.uploadAdImage(it) }

        val features = request.featureIds.map { featureRepository.findById(it).orElseThrow() }

        return adRepository.save(CarAdEntity(
            userId = request.userId,
            make = request.make,
            model = request.model,
            title = request.title,
            description = request.description,
            year = request.year,
            color = request.color,
            hp = request.hp,
            displacement = request.displacement,
            mileage = request.mileage,
            price = request.price,
            doorCount = request.doorCount,
            ownerCount = request.ownerCount,
            phone = request.phone,
            location = request.location,
            imageUrls = imageUrls.toMutableList(),
            features = features.toMutableList()
        ))
    }

    @Transactional
    fun updateAd(id: Long, request: UpdateAdRequest): CarAdEntity {
        val user = userRepository.findById(request.userId)
            .orElseThrow { RuntimeException("User not found") }

        val ad = adRepository.findById(id).orElseThrow{ RuntimeException("Ad not found") }

        if (ad.userId != request.userId) {
            throw RuntimeException("You are not authorized to modify this ad")
        }

        request.make?.let { ad.make = it }
        request.model?.let { ad.model = it }
        request.title?.let { ad.title = it }
        request.description?.let { ad.description = it}
        request.year?.let { ad.year = it }
        request.color?.let { ad.color = it }
        request.hp?.let { ad.hp = it }
        request.displacement?.let { ad.displacement = it }
        request.mileage?.let { ad.mileage = it }
        request.price?.let { ad.price = it }
        request.doorCount?.let { ad.doorCount = it }
        request.ownerCount?.let { ad.ownerCount = it }
        request.phone?.let { ad.phone = it }
        request.location?.let { ad.location = it }

        request.imagesToDelete.forEach { storageService.deleteImage(it) }
        val newUrls = request.newImages.map { storageService.uploadAdImage(it) }
        ad.imageUrls.addAll(newUrls)

        request.featureIds?.let { ids ->
            ad.features = ids.map { featureRepository.findById(it).orElseThrow() }.toMutableList()
        }

        return adRepository.save(ad)
    }

    fun getAdById(id: Long): CarAdEntity? {
        return adRepository.findById(id).orElse(null)
    }

    fun getAdsByUserId(userId: String): List<CarAdEntity> {
        return adRepository.findByUserId(userId)
    }

    fun getFilteredAds(filters: CarAdFilters): List<CarAdEntity> {
        return adRepository.findAll(
            Specification.where(
                CarAdSpecifications.withUserId(filters.userId)
            ).and(
                CarAdSpecifications.withMake(filters.make)
            ).and(
                CarAdSpecifications.withModel(filters.model)
            ).and(
                CarAdSpecifications.withYearBetween(filters.minYear, filters.maxYear)
            ).and(
                CarAdSpecifications.withColor(filters.color)
            ).and(
                CarAdSpecifications.withHpBetween(filters.minHp, filters.maxHp)
            ).and(
                CarAdSpecifications.withDisplacementBetween(filters.minDisplacement, filters.maxDisplacement)
            ).and(
                CarAdSpecifications.withMileageBetween(filters.minMileage, filters.maxMileage)
            ).and(
                CarAdSpecifications.withOwnerCountBetween(filters.minOwnerCount, filters.maxOwnerCount)
            ).and(
                CarAdSpecifications.withPriceBetween(filters.minPrice, filters.maxPrice)
            ).and(
                CarAdSpecifications.withDoorCount(filters.doorCount)
            ).and(
                CarAdSpecifications.withLocation(filters.location)
            ).and(
                CarAdSpecifications.withFeatures(filters.features)
            ).and(
                CarAdSpecifications.hasImages(filters.hasImages)
            ).and(
                CarAdSpecifications.withConditions(filters.conditions)
            )
        )
    }
}