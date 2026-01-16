package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.FuelTypeEntity
import com.drivebuy.security.RequiresApiKey
import com.drivebuy.service.car_info.FuelTypeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/fuelTypes")
class FuelTypeController(private val fuelTypeService: FuelTypeService) {

    @GetMapping("/id/{id}")
    fun getFuelTypeById(@PathVariable id: Long): FuelTypeEntity? {
        return fuelTypeService.getFuelTypeById(id)
    }

    @GetMapping("/fuelType/{fuelTypeName}")
    fun getFuelTypeByTypeName(@PathVariable fuelTypeName: String): FuelTypeEntity? {
        return fuelTypeService.getFuelTypeByTypeName(fuelTypeName)
    }

    @GetMapping
    fun getAllFuelTypes(): List<FuelTypeEntity?> {
        return fuelTypeService.getAllFuelTypes()
    }

    @RequiresApiKey
    @PutMapping("/update/{fuelTypeId}")
    fun updateFuelType(
        @PathVariable fuelTypeId: Long,
        @RequestParam fuelTypeName: String): FuelTypeEntity {
        return fuelTypeService.updateFuelType(fuelTypeId, fuelTypeName)
    }

    @RequiresApiKey
    @DeleteMapping("/delete/{fuelTypeId}")
    fun deleteFuelType(@PathVariable fuelTypeId: Long) {
        fuelTypeService.deleteFuelType(fuelTypeId)
    }
}