package com.drivebuy.service

import com.drivebuy.persistance.dto.CarAdDto
import com.drivebuy.persistance.dto.CarAdFilters
import com.drivebuy.repository.AdRepository
import com.drivebuy.persistance.entity.CarAdEntity
import com.drivebuy.persistance.request.CreateAdRequest
import com.drivebuy.persistance.request.UpdateAdRequest
import com.drivebuy.repository.UserRepository
import com.drivebuy.repository.specifications.CarAdSpecifications
import jakarta.transaction.Transactional
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class AdService(
    private val adRepository: AdRepository,
    private val userRepository: UserRepository,
    private val storageService: FirebaseStorageService
) {
    @Transactional
    fun createAd(request: CreateAdRequest): CarAdEntity {
        val user = try {
            userRepository.findByFirebaseId(request.userId)
        } catch (exception: Exception) {
            throw RuntimeException("User not found. Register or login to create an ad.")
        }

        val imageUrls = request.images.map { storageService.uploadAdImage(it) }
        //val features = featureRepository.findByFeatureNameIn(request.features)

        println("""
    userId = ${user.firebaseId},
    make = ${request.make},
    model = ${request.model},
    title = ${request.title},
    description = ${request.description},
    year = ${request.year},
    color = ${request.color},
    hp = ${request.hp},
    bodyType = ${request.bodyType},
    condition = ${request.condition},
    displacement = ${request.displacement},
    mileage = ${request.mileage},
    price = ${request.price},
    doorCount = ${request.doorCount},
    cylinderCount = ${request.cylinderCount},
    ownerCount = ${request.ownerCount},
    phone = ${request.phone},
    region = ${request.region},
    city = ${request.city},
    imageUrls = ${imageUrls.toMutableList()},
    features = ${request.features.toMutableList()}
""".trimIndent())
        return adRepository.save(CarAdEntity(
            userId = user.firebaseId,
            make = request.make,
            model = request.model,
            title = request.title,
            description = request.description,
            year = request.year,
            color = request.color,
            bodyType = request.bodyType,
            condition = request.condition,
            hp = request.hp,
            displacement = request.displacement,
            mileage = request.mileage,
            price = request.price,
            doorCount = request.doorCount,
            cylinderCount = request.cylinderCount,
            transmissionType = request.transmissionType,
            fuelType = request.fuelType,
            steeringPosition = request.steeringPosition,
            ownerCount = request.ownerCount,
            phone = request.phone,
            region = request.region,
            city = request.city,
            imageUrls = imageUrls.toMutableList(),
            features = request.features.toMutableList(),
        ))
    }

    @Transactional
    fun updateAd(id: Long, request: UpdateAdRequest): CarAdEntity {
        val user = try {
            userRepository.findByFirebaseId(request.userId)
        } catch (exception: Exception) {
            throw RuntimeException("User not found. Register or login to create an ad.")
        }
        val ad = adRepository.findById(id).orElseThrow{ RuntimeException("Ad not found") }

        if (ad.userId != user.firebaseId) {
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
        request.region?.let { ad.region = it }
        request.city?.let { ad.city = it }
        request.features?.let { ad.features = it.toMutableList() }

        request.imagesToDelete.forEach { storageService.deleteImage(it) }
        val newUrls = request.newImages.map { storageService.uploadAdImage(it) }
        ad.imageUrls.addAll(newUrls)

        return adRepository.save(ad)
    }

    @Transactional
    fun deleteAd(id: Long, userId: String) {
        val user = try {
            userRepository.findByFirebaseId(userId)
        } catch (exception: Exception) {
            throw RuntimeException("User not found. Register or login to create an ad.")
        }

        val ad = adRepository.findById(id).orElseThrow{ RuntimeException("Ad not found") }

        if (ad.userId != user.firebaseId) {
            throw RuntimeException("You are not authorized to modify this ad")
        }

        return adRepository.delete(ad)
    }

    fun getAdById(id: Long): CarAdEntity? {
        return adRepository.findById(id).orElse(null)
    }

    fun getAdByIdWithUserInfo(id: Long): CarAdDto? {
        val ad = adRepository.findById(id).orElse(null) ?: return null
        val seller = userRepository.findById(ad.userId).orElse(null) ?: return null
        return CarAdDto.fromEntity(ad, seller)
    }

    fun getAdsByUserId(userId: String): List<CarAdEntity> {
        return adRepository.findByUserId(userId)
    }

    fun getFilteredAds(filters: CarAdFilters): List<CarAdEntity> {
        val specification = Specification.where(
            CarAdSpecifications.withUserId(filters.userId)
        ).and(
            CarAdSpecifications.withKeyword(filters.keyword)
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
            CarAdSpecifications.withCylinderCount(filters.cylinderCount)
        ).and(
            CarAdSpecifications.withRegion(filters.region)
        ).and(
            CarAdSpecifications.withCity(filters.city)
        ).and(
            CarAdSpecifications.withFeatures(filters.features)
        ).and(
            CarAdSpecifications.hasImages(filters.hasImages)
        ).and(
            CarAdSpecifications.withConditions(filters.conditions)
        ).and(
            CarAdSpecifications.withTransmissionType(filters.transmissionType)
        ).and(
            CarAdSpecifications.withBodyType(filters.bodyType)
        ).and(
            CarAdSpecifications.withFuelType(filters.fuelType)
        ).and(
            CarAdSpecifications.withSteeringPosition(filters.steeringPosition)
        )

        // Apply sorting if specified
        val sort = filters.sortBy?.let { sortOption ->
            if (sortOption.isAscending()) {
                Sort.by(Sort.Direction.ASC, sortOption.fieldName)
            } else {
                Sort.by(Sort.Direction.DESC, sortOption.fieldName)
            }
        } ?: Sort.by(Sort.Direction.DESC, "createdAt") // Default sort by newest first

        return adRepository.findAll(specification, sort)
    }

    fun getFilteredAdsWithUserInfo(filters: CarAdFilters): List<CarAdDto> {
        val ads = getFilteredAds(filters)
        
        // Get all unique user IDs from the ads
        val userIds = ads.map { it.userId }.distinct()
        
        // Fetch all users in one query
        val users = userRepository.findAllById(userIds)
        val userMap = users.associateBy { it.firebaseId }
        
        // Convert to CarAdDto with user info
        return CarAdDto.fromEntities(ads, userMap)
    }
}