package com.drivebuy.persistance.request

import org.springframework.web.multipart.MultipartFile

data class UpdateAdRequest(
    val userId: String,
    val make: String? = null,
    val model: String? = null,
    val title: String? = null,
    val description: String? = null,
    val year: Int? = null,
    val color: String? = null,
    val hp: Int? = null,
    val displacement: Int? = null,
    val mileage: Int? = null,
    val price: Int? = null,
    val bodyType: String? = null,
    val condition: String? = null,
    val doorCount: String? = null,
    val cylinderCount: String? = null,
    val transmissionType: String? = null,
    val fuelType: String? = null,
    val steeringPosition: String? = null,
    val driveType: String? = null,
    val ownerCount: Int? = null,
    val phone: String? = null,
    val region: String? = null,
    val city: String? = null,
    val features: List<String>? = null,
    val newImages: List<MultipartFile> = emptyList(),
    val imagesToDelete: List<String> = emptyList()
) {
    init {
        year?.let {
            val currentYear = java.time.Year.now().value
            require(it in 1900..currentYear) {
                "Year must be between 1900 and $currentYear"
            }
        }
    }
}