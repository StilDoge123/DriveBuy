package com.drivebuy.persistance.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

data class CreateAdRequest(
    @field:NotBlank(message = "User ID is required")
    val userId: String,
    
    @field:NotBlank(message = "Make is required")
    val make: String,
    
    @field:NotBlank(message = "Model is required")
    val model: String,
    
    val title: String? = null,
    val description: String? = null,
    
    @field:NotNull(message = "Year is required")
    @field:Min(value = 1900, message = "Year must be at least 1900")
    @field:Max(value = 2024, message = "Year cannot be in the future")
    val year: Int,
    
    val color: String? = null,
    
    @field:NotNull(message = "Horsepower is required")
    @field:Min(value = 1, message = "Horsepower must be at least 1")
    @field:Max(value = 10000, message = "Horsepower cannot exceed 10000")
    val hp: Int,
    
    @field:NotNull(message = "Displacement is required")
    @field:Min(value = 1, message = "Displacement must be at least 1")
    @field:Max(value = 100000, message = "Displacement cannot exceed 100000")
    val displacement: Int,
    
    @field:NotNull(message = "Mileage is required")
    @field:Min(value = 1, message = "Mileage must be at least 1")
    val mileage: Int,
    
    @field:NotNull(message = "Price is required")
    @field:Min(value = 1, message = "Price must be at least 1")
    val price: Int,
    
    val bodyType: String? = null,
    val transmissionType: String? = null,
    val fuelType: String? = null,
    val condition: String? = null,
    val doorCount: String? = null,
    val steeringPosition: String? = null,
    val cylinderCount: String? = null,
    val driveType: String? = null,
    
    @field:NotNull(message = "Owner count is required")
    @field:Min(value = 0, message = "Owner count cannot be negative")
    val ownerCount: Int,
    
    val phone: String? = null,
    val region: String? = null,
    val city: String? = null,
    val features: List<String> = emptyList(),
    val images: List<MultipartFile> = emptyList()
)