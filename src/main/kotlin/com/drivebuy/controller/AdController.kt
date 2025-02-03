package com.drivebuy.controller

import com.drivebuy.persistance.entity.CarAdEntity
import com.drivebuy.service.AdService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ads")
class AdController(private val adService: AdService) {

    @PostMapping
    fun createAd(@RequestBody ad: CarAdEntity): CarAdEntity {
        return adService.createAd(ad)
    }

    @GetMapping("/{id}")
    fun getAd(@PathVariable id: Long): CarAdEntity? {
        return adService.getAdById(id)
    }

    @GetMapping("/user/{userId}")
    fun getAdsByUserId(@PathVariable userId: String): List<CarAdEntity> {
        return adService.getAdsByUserId(userId)
    }
}