package com.drivebuy.controller

import com.drivebuy.persistance.dto.CarAdFilters
import com.drivebuy.persistance.entity.CarAdEntity
import com.drivebuy.persistance.request.CreateAdRequest
import com.drivebuy.persistance.request.UpdateAdRequest
import com.drivebuy.service.AdService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/ads")
class AdController(
    private val adService: AdService,
    private val objectMapper: ObjectMapper
) {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createAd(
        @RequestPart("data") requestJson: String,
        @RequestPart("images") images: List<MultipartFile>
    ): CarAdEntity {
        val request = objectMapper.readValue(requestJson, CreateAdRequest::class.java)
            .copy(images = images)
        return adService.createAd(request)
    }

    @PatchMapping(path = ["/{id}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateAd(
        @PathVariable id: Long,
        @RequestPart("data") requestJson: String,
        @RequestPart(value = "newImages", required = false) newImages: List<MultipartFile>?,
        @RequestPart(value = "imagesToDelete", required = false) imagesToDelete: List<String>?
    ): CarAdEntity {
        val request = objectMapper.readValue(requestJson, UpdateAdRequest::class.java).copy(
            newImages = newImages ?: emptyList(),
            imagesToDelete = imagesToDelete ?: emptyList()
        )
        return adService.updateAd(id, request)
    }

    @GetMapping("/{id}")
    fun getAd(@PathVariable id: Long): CarAdEntity? {
        return adService.getAdById(id)
    }

    @GetMapping("/user/{userId}")
    fun getAdsByUserId(@PathVariable userId: String): List<CarAdEntity> {
        return adService.getAdsByUserId(userId)
    }

    @GetMapping("/filter")
    fun getFilteredAds(
        @RequestParam(required = false) userId: String?,
        @RequestParam(required = false) make: String?,
        @RequestParam(required = false) model: String?,
        @RequestParam(required = false) minYear: Int?,
        @RequestParam(required = false) maxYear: Int?,
        @RequestParam(required = false) color: String?,
        @RequestParam(required = false) minHp: Int?,
        @RequestParam(required = false) maxHp: Int?,
        @RequestParam(required = false) minDisplacement: Int?,
        @RequestParam(required = false) maxDisplacement: Int?,
        @RequestParam(required = false) minMileage: Int?,
        @RequestParam(required = false) maxMileage: Int?,
        @RequestParam(required = false) minOwnerCount: Int?,
        @RequestParam(required = false) maxOwnerCount: Int?,
        @RequestParam(required = false) minPrice: Int?,
        @RequestParam(required = false) maxPrice: Int?,
        @RequestParam(required = false) doorCount: Int?,
        @RequestParam(required = false) phone: String?,
        @RequestParam(required = false) location: String?,
        @RequestParam(required = false) features: List<String>?,
        @RequestParam(required = false) hasImages: Boolean?
    ): List<CarAdEntity> {
        val filters = CarAdFilters(
            userId = userId,
            make = make,
            model = model,
            minYear = minYear,
            maxYear = maxYear,
            color = color,
            minHp = minHp,
            maxHp = maxHp,
            minDisplacement = minDisplacement,
            maxDisplacement = maxDisplacement,
            minMileage = minMileage,
            maxMileage = maxMileage,
            minOwnerCount = minOwnerCount,
            maxOwnerCount = maxOwnerCount,
            minPrice = minPrice,
            maxPrice = maxPrice,
            doorCount = doorCount,
            location = location,
            features = features,
            hasImages = hasImages
        )
        return adService.getFilteredAds(filters)
    }
}