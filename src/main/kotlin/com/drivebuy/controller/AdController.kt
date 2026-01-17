package com.drivebuy.controller

import com.drivebuy.persistance.dto.CarAdDto
import com.drivebuy.persistance.dto.CarAdFilters
import com.drivebuy.persistance.dto.SortOption
import com.drivebuy.persistance.entity.CarAdEntity
import com.drivebuy.persistance.request.CreateAdRequest
import com.drivebuy.persistance.request.UpdateAdRequest
import com.drivebuy.service.AdService
import com.drivebuy.util.AuthUtil.getUidFromRequest
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
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
        @RequestPart("images") images: List<MultipartFile>,
        request: HttpServletRequest
    ): CarAdEntity {
        val uid = getUidFromRequest(request)
        val requestObj = objectMapper.readValue(requestJson, CreateAdRequest::class.java)
            .copy(images = images, userId = uid)
        return adService.createAd(requestObj)
    }

    @PatchMapping(path = ["/{id}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateAd(
        @PathVariable id: Long,
        @RequestParam("data") requestJson: String,
        @RequestPart(value = "newImages", required = false) newImages: List<MultipartFile>?,
        @RequestParam(value = "imagesToDelete", required = false) imagesToDelete: List<String>?,
        request: HttpServletRequest
    ): CarAdEntity {
        val uid = getUidFromRequest(request)
        val requestObj = objectMapper.readValue(requestJson, UpdateAdRequest::class.java).copy(
            newImages = newImages ?: emptyList(),
            imagesToDelete = imagesToDelete ?: emptyList(),
            userId = uid
        )
        return adService.updateAd(id, requestObj)
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteAd(
        @PathVariable id: Long,
        request: HttpServletRequest
    ) {
        val uid = getUidFromRequest(request)
        return adService.deleteAd(id, uid)
    }

    @GetMapping("/{id}")
    fun getAd(@PathVariable id: Long): CarAdEntity? {
        return adService.getAdById(id)
    }

    @GetMapping("/{id}/with-user-info")
    fun getAdWithUserInfo(@PathVariable id: Long): CarAdDto? {
        return adService.getAdByIdWithUserInfo(id)
    }

    @GetMapping("/user/{userId}")
    fun getAdsByUserId(@PathVariable userId: String): List<CarAdEntity> {
        return adService.getAdsByUserId(userId)
    }

    @GetMapping("/filter")
    fun getFilteredAds(
        @RequestParam(required = false) userId: String?,
        @RequestParam(required = false) keyword: String?,
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
        @RequestParam(required = false) doorCount: String?,
        @RequestParam(required = false) transmissionType: String?,
        @RequestParam(required = false) bodyType: String?,
        @RequestParam(required = false) fuelType: String?,
        @RequestParam(required = false) steeringPosition: String?,
        @RequestParam(required = false) cylinderCount: String?,
        @RequestParam(required = false) driveType: String?,
        @RequestParam(required = false) phone: String?,
        @RequestParam(required = false) region: String?,
        @RequestParam(required = false) city: String?,
        @RequestParam(required = false) features: List<String>?,
        @RequestParam(required = false) conditions: List<String>?,
        @RequestParam(required = false) hasImages: Boolean?,
        @RequestParam(required = false) sortBy: SortOption?
    ): List<CarAdEntity> {
        val filters = CarAdFilters(
            userId = userId,
            keyword = keyword,
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
            transmissionType = transmissionType,
            bodyType = bodyType,
            fuelType = fuelType,
            steeringPosition = steeringPosition,
            cylinderCount = cylinderCount,
            driveType = driveType,
            region = region,
            city = city,
            features = features,
            conditions = conditions,
            hasImages = hasImages,
            sortBy = sortBy
        )
        return adService.getFilteredAds(filters)
    }

    @GetMapping("/all")
    fun getAllAds(
        @RequestParam(required = false) sortBy: SortOption?
    ): List<CarAdEntity> {
        val filters = CarAdFilters(sortBy = sortBy)
        return adService.getFilteredAds(filters)
    }

    @GetMapping("/with-user-info")
    fun getAdsWithUserInfo(
        @RequestParam(required = false) userId: String?,
        @RequestParam(required = false) keyword: String?,
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
        @RequestParam(required = false) doorCount: String?,
        @RequestParam(required = false) transmissionType: String?,
        @RequestParam(required = false) bodyType: String?,
        @RequestParam(required = false) fuelType: String?,
        @RequestParam(required = false) steeringPosition: String?,
        @RequestParam(required = false) cylinderCount: String?,
        @RequestParam(required = false) driveType: String?,
        @RequestParam(required = false) phone: String?,
        @RequestParam(required = false) region: String?,
        @RequestParam(required = false) city: String?,
        @RequestParam(required = false) features: List<String>?,
        @RequestParam(required = false) conditions: List<String>?,
        @RequestParam(required = false) hasImages: Boolean?,
        @RequestParam(required = false) sortBy: SortOption?
    ): List<CarAdDto> {
        val filters = CarAdFilters(
            userId = userId,
            keyword = keyword,
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
            transmissionType = transmissionType,
            bodyType = bodyType,
            fuelType = fuelType,
            steeringPosition = steeringPosition,
            cylinderCount = cylinderCount,
            driveType = driveType,
            region = region,
            city = city,
            features = features,
            conditions = conditions,
            hasImages = hasImages,
            sortBy = sortBy
        )
        return adService.getFilteredAdsWithUserInfo(filters)
    }
}