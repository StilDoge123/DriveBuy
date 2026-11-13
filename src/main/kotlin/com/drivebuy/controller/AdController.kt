package com.drivebuy.controller

import com.drivebuy.persistance.dto.CarAdDto
import com.drivebuy.persistance.dto.CarAdFilters
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
    fun getFilteredAds(filters: CarAdFilters): List<CarAdEntity> {
        return adService.getFilteredAds(filters)
    }

    @GetMapping("/all")
    fun getAllAds(filters: CarAdFilters): List<CarAdEntity> {
        return adService.getFilteredAds(filters)
    }

    @GetMapping("/with-user-info")
    fun getAdsWithUserInfo(filters: CarAdFilters): List<CarAdDto> {
        return adService.getFilteredAdsWithUserInfo(filters)
    }
}