package com.drivebuy.persistance.dto

import com.drivebuy.persistance.response.UserResponse
import java.time.LocalDateTime

data class CarAdDto(
    val id: Long,
    val title: String?,
    val description: String?,
    val make: String,
    val model: String,
    val year: Int,
    val color: String?,
    val hp: Int,
    val displacement: Int,
    val mileage: Int,
    val price: Int,
    val bodyType: String?,
    val condition: String?,
    val doorCount: String?,
    val cylinderCount: String?,
    val transmissionType: String?,
    val fuelType: String?,
    val steeringPosition: String?,
    val driveType: String?,
    val ownerCount: Int,
    val phone: String?,
    val region: String?,
    val city: String?,
    val imageUrls: List<String>,
    val features: List<String>,
    val createdAt: LocalDateTime,
    val seller: UserResponse
) {
    companion object {
        fun fromEntity(
            ad: com.drivebuy.persistance.entity.CarAdEntity, 
            seller: com.drivebuy.persistance.entity.UserEntity
        ): CarAdDto {
            return CarAdDto(
                id = ad.id,
                title = ad.title,
                description = ad.description,
                make = ad.make,
                model = ad.model,
                year = ad.year,
                color = ad.color,
                hp = ad.hp,
                displacement = ad.displacement,
                mileage = ad.mileage,
                price = ad.price,
                bodyType = ad.bodyType,
                condition = ad.condition,
                doorCount = ad.doorCount,
                cylinderCount = ad.cylinderCount,
                transmissionType = ad.transmissionType,
                fuelType = ad.fuelType,
                steeringPosition = ad.steeringPosition,
                driveType = ad.driveType,
                ownerCount = ad.ownerCount,
                phone = ad.phone,
                region = ad.region,
                city = ad.city,
                imageUrls = ad.imageUrls.toList(),
                features = ad.features.toList(),
                createdAt = ad.createdAt,
                seller = UserResponse.fromEntity(seller)
            )
        }

        fun fromEntities(
            ads: List<com.drivebuy.persistance.entity.CarAdEntity>,
            userLookup: Map<String, com.drivebuy.persistance.entity.UserEntity>
        ): List<CarAdDto> {
            return ads.mapNotNull { ad ->
                userLookup[ad.userId]?.let { seller ->
                    fromEntity(ad, seller)
                }
            }
        }
    }
}
