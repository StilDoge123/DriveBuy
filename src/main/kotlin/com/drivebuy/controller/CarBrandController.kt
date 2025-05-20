package com.drivebuy.controller

import com.drivebuy.persistance.entity.CarBrandEntity
import com.drivebuy.service.CarBrandService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/brands")
class CarBrandController(private val carBrandService: CarBrandService) {
    @GetMapping("/id/{id}")
    fun getBrandById(@PathVariable id: Long): CarBrandEntity? {
        return carBrandService.getBrandById(id)
    }

    @GetMapping("/brandName/{brandName}")
    fun getBrandByName(@PathVariable brandName: String): CarBrandEntity? {
        return carBrandService.getBrandByName(brandName)
    }

    @GetMapping
    fun getAllBrands(): List<CarBrandEntity?> {
        return carBrandService.getAllBrands()
    }
}