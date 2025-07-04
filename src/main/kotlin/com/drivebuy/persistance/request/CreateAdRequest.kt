package com.drivebuy.persistance.request

import org.springframework.web.multipart.MultipartFile

data class CreateAdRequest(
    val userId: String,
    val make: String,
    val model: String,
    val title: String,
    val description: String,
    val year: Int,
    val color: String,
    val hp: Int,
    val displacement: Int,
    val mileage: Int,
    val price: Int,
    val bodyType: String,
    val transmissionType: String,
    val fuelType: String,
    val condition: String,
    val doorCount: String,
    val steeringPosition: String,
    val cylinderCount: String,
    val ownerCount: Int,
    val phone: String,
    val region: String,
    val city: String,
    val features: List<String> = emptyList(),
    val images: List<MultipartFile> = emptyList()
)