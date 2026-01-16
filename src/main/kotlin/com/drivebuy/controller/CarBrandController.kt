package com.drivebuy.controller

import com.drivebuy.persistance.entity.CarBrandEntity
import com.drivebuy.security.RequiresApiKey
import com.drivebuy.service.CarBrandService
import org.springframework.web.bind.annotation.*

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

    @RequiresApiKey
    @PostMapping("/create")
    fun createCarBrand(@RequestParam brandName: String): CarBrandEntity {
        return carBrandService.createCarBrand(brandName)
    }

    @RequiresApiKey
    @PutMapping("/update/{brandId}")
    fun updateCarBrand(
        @PathVariable brandId: Long,
        @RequestParam brandName: String): CarBrandEntity {
        return carBrandService.updateCarBrand(brandId, brandName)
    }

    @RequiresApiKey
    @DeleteMapping("/delete/{brandId}")
    fun deleteCarBrand(@PathVariable brandId: Long) {
        carBrandService.deleteCarBrand(brandId)
    }
}