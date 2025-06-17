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
    val doorCount: Int,
    val ownerCount: Int,
    val phone: String,
    val location: String,
    val featureIds: List<Long> = emptyList(),
    val images: List<MultipartFile> = emptyList()
)