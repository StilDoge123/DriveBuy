package com.drivebuy.repository.specifications

import com.drivebuy.persistance.entity.CarAdEntity
import com.drivebuy.persistance.entity.car_info.CarFeaturesEntity
import org.springframework.data.jpa.domain.Specification

class CarAdSpecifications {
    companion object {
        // User filters
        fun withUserId(userId: String?): Specification<CarAdEntity> = Specification { root, _, cb ->
            userId?.let { cb.equal(root.get<String>("userId"), it) } ?: cb.conjunction()
        }

        // Basic car info
        fun withMake(make: String?): Specification<CarAdEntity> = Specification { root, _, cb ->
            make?.let { cb.equal(root.get<String>("make"), it) } ?: cb.conjunction()
        }

        fun withModel(model: String?): Specification<CarAdEntity> = Specification { root, _, cb ->
            model?.let { cb.equal(root.get<String>("model"), it) } ?: cb.conjunction()
        }

        fun withYearBetween(min: Int?, max: Int?): Specification<CarAdEntity> = Specification { root, _, cb ->
            when {
                min != null && max != null -> cb.between(root.get("year"), min, max)
                min != null -> cb.greaterThanOrEqualTo(root.get("year"), min)
                max != null -> cb.lessThanOrEqualTo(root.get("year"), max)
                else -> cb.conjunction()
            }
        }

        fun withColor(color: String?): Specification<CarAdEntity> = Specification { root, _, cb ->
            color?.let { cb.equal(root.get<String>("color"), it) } ?: cb.conjunction()
        }

        // Technical specs
        fun withHpBetween(min: Int?, max: Int?): Specification<CarAdEntity> = Specification { root, _, cb ->
            when {
                min != null && max != null -> cb.between(root.get("hp"), min, max)
                min != null -> cb.greaterThanOrEqualTo(root.get("hp"), min)
                max != null -> cb.lessThanOrEqualTo(root.get("hp"), max)
                else -> cb.conjunction()
            }
        }

        fun withDisplacementBetween(min: Int?, max: Int?): Specification<CarAdEntity> = Specification { root, _, cb ->
            when {
                min != null && max != null -> cb.between(root.get("displacement"), min, max)
                min != null -> cb.greaterThanOrEqualTo(root.get("displacement"), min)
                max != null -> cb.lessThanOrEqualTo(root.get("displacement"), max)
                else -> cb.conjunction()
            }
        }

        // Usage info
        fun withMileageBetween(min: Int?, max: Int?): Specification<CarAdEntity> = Specification { root, _, cb ->
            when {
                min != null && max != null -> cb.between(root.get("mileage"), min, max)
                min != null -> cb.greaterThanOrEqualTo(root.get("mileage"), min)
                max != null -> cb.lessThanOrEqualTo(root.get("mileage"), max)
                else -> cb.conjunction()
            }
        }

        fun withOwnerCountBetween(min: Int?, max: Int?): Specification<CarAdEntity> = Specification { root, _, cb ->
            when {
                min != null && max != null -> cb.between(root.get("ownerCount"), min, max)
                min != null -> cb.greaterThanOrEqualTo(root.get("ownerCount"), min)
                max != null -> cb.lessThanOrEqualTo(root.get("ownerCount"), max)
                else -> cb.conjunction()
            }
        }

        // Pricing
        fun withPriceBetween(min: Int?, max: Int?): Specification<CarAdEntity> = Specification { root, _, cb ->
            when {
                min != null && max != null -> cb.between(root.get("price"), min, max)
                min != null -> cb.greaterThanOrEqualTo(root.get("price"), min)
                max != null -> cb.lessThanOrEqualTo(root.get("price"), max)
                else -> cb.conjunction()
            }
        }

        // Physical attributes
        fun withDoorCount(doorCount: Int?): Specification<CarAdEntity> = Specification { root, _, cb ->
            doorCount?.let { cb.equal(root.get<Int>("doorCount"), it) } ?: cb.conjunction()
        }

        // Contact info
        fun withLocation(location: String?): Specification<CarAdEntity> = Specification { root, _, cb ->
            location?.let { cb.equal(root.get<String>("location"), it) } ?: cb.conjunction()
        }

        // Feature
        fun withFeatures(features: List<String>?): Specification<CarAdEntity> = Specification { root, _, cb ->
            features?.takeIf { it.isNotEmpty() }?.let {
                val featuresJoin = root.join<CarAdEntity, CarFeaturesEntity>("features")
                featuresJoin.get<String>("featureName").`in`(it)
            } ?: cb.conjunction()
        }

        // Image presence
        fun hasImages(hasImages: Boolean?): Specification<CarAdEntity> = Specification { root, _, cb ->
            hasImages?.let {
                if (it) {
                    cb.isNotEmpty(root.get<List<String>>("imageUrls"))
                } else {
                    cb.isEmpty(root.get<List<String>>("imageUrls"))
                }
            } ?: cb.conjunction()
        }
    }
}