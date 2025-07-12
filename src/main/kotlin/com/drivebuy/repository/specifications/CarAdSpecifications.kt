package com.drivebuy.repository.specifications

import com.drivebuy.persistance.entity.CarAdEntity
import com.drivebuy.persistance.entity.car_info.CarConditionEntity
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
        fun withDoorCount(doorCount: String?): Specification<CarAdEntity> = Specification { root, _, cb ->
            doorCount?.let { cb.equal(root.get<String>("doorCount"), it) } ?: cb.conjunction()
        }

        fun withCylinderCount(cylinderCount: String?): Specification<CarAdEntity> = Specification { root, _, cb ->
            cylinderCount?.let { cb.equal(root.get<String>("cylinderCount"), it) } ?: cb.conjunction()
        }

        // Location
        fun withRegion(region: String?): Specification<CarAdEntity> = Specification { root, _, cb ->
            region?.let { cb.equal(root.get<String>("region"), it) } ?: cb.conjunction()
        }
        fun withCity(city: String?): Specification<CarAdEntity> = Specification { root, _, cb ->
            city?.let { cb.equal(root.get<String>("city"), it) } ?: cb.conjunction()
        }

        // Condition
        fun withConditions(conditions: List<String>?): Specification<CarAdEntity> = Specification { root, _, cb ->
            conditions?.takeIf { it.isNotEmpty() }?.let {
                val conditionsJoin = root.join<CarAdEntity, CarConditionEntity>("conditions")
                conditionsJoin.get<String>("conditionName").`in`(it)
            } ?: cb.conjunction()
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

        fun withTransmissionType(transmissionType: String?): Specification<CarAdEntity> = Specification { root, _, cb ->
            transmissionType?.let { cb.equal(root.get<String>("transmissionType"), it) } ?: cb.conjunction()
        }

        fun withBodyType(bodyType: String?): Specification<CarAdEntity> = Specification { root, _, cb ->
            bodyType?.let { cb.equal(root.get<String>("bodyType"), it) } ?: cb.conjunction()
        }

        fun withFuelType(fuelType: String?): Specification<CarAdEntity> = Specification { root, _, cb ->
            fuelType?.let { cb.equal(root.get<String>("fuelType"), it) } ?: cb.conjunction()
        }

        fun withSteeringPosition(steeringPosition: String?): Specification<CarAdEntity> = Specification { root, _, cb ->
            steeringPosition?.let { cb.equal(root.get<String>("steeringPosition"), it) } ?: cb.conjunction()
        }

        fun withKeyword(keyword: String?): Specification<CarAdEntity> = Specification { root, _, cb ->
            keyword?.let { searchTerm ->
                val searchPattern = "%${searchTerm.lowercase()}%"
                cb.or(
                    cb.like(cb.lower(root.get("title")), searchPattern),
                    cb.like(cb.lower(root.get("description")), searchPattern),
                    cb.like(cb.lower(root.get("make")), searchPattern),
                    cb.like(cb.lower(root.get("model")), searchPattern),
                    cb.like(cb.lower(root.get("color")), searchPattern),
                    cb.like(cb.lower(root.get("region")), searchPattern),
                    cb.like(cb.lower(root.get("city")), searchPattern),
                    cb.like(cb.lower(root.get("transmissionType")), searchPattern),
                    cb.like(cb.lower(root.get("fuelType")), searchPattern),
                    cb.like(cb.lower(root.get("bodyType")), searchPattern),
                    cb.like(cb.lower(root.get("condition")), searchPattern),
                    cb.like(cb.lower(root.get("features")), searchPattern)
                )
            } ?: cb.conjunction()
        }
    }
}