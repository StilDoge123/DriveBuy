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
    val doorCount: String? = null,
    val ownerCount: Int? = null,
    val cylinderCount: String? = null,
    val phone: String? = null,
    val region: String? = null,
    val city: String? = null,
    val features: List<String>? = null,
    val newImages: List<MultipartFile> = emptyList(),
    val imagesToDelete: List<String> = emptyList()
)