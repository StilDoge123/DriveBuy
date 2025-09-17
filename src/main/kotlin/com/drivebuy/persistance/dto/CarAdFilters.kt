package com.drivebuy.persistance.dto

data class CarAdFilters(
    // User filters
    val userId: String? = null,

    // Keyword search
    val keyword: String? = null,

    // Basic car info
    val make: String? = null,
    val model: String? = null,
    val minYear: Int? = null,
    val maxYear: Int? = null,
    val color: String? = null,

    // Technical specs
    val minHp: Int? = null,
    val maxHp: Int? = null,
    val minDisplacement: Int? = null,
    val maxDisplacement: Int? = null,

    // Usage info
    val minMileage: Int? = null,
    val maxMileage: Int? = null,
    val minOwnerCount: Int? = null,
    val maxOwnerCount: Int? = null,

    // Pricing
    val minPrice: Int? = null,
    val maxPrice: Int? = null,

    // Physical attributes
    val doorCount: String? = null,
    val cylinderCount: String? = null,
    val transmissionType: String? = null,
    val fuelType: String? = null,
    val bodyType: String? = null,
    val steeringPosition: String? = null,
    val driveType: String? = null,

    // Contact info
    val region: String? = null,
    val city: String? = null,

    // Features (exact match)
    val features: List<String>? = null,

    // Conditions (exact match)
    val conditions: List<String>? = null,

    // Image presence
    val hasImages: Boolean? = null,

    // Sorting
    val sortBy: SortOption? = null
)