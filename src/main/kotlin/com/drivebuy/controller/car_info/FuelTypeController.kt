package com.drivebuy.controller.car_info

import com.drivebuy.persistance.entity.car_info.FuelTypeEntity
import com.drivebuy.service.car_info.FuelTypeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/fuelTypes")
class FuelTypeController(private val fuelTypeService: FuelTypeService) {

    @GetMapping("/{id}")
    fun getFuelTypeById(@PathVariable id: Long): FuelTypeEntity? {
        return fuelTypeService.getFuelTypeById(id)
    }

    @GetMapping("/{fuelTypeName}")
    fun getFuelTypeByTypeName(@PathVariable fuelTypeName: String): FuelTypeEntity? {
        return fuelTypeService.getFuelTypeByTypeName(fuelTypeName)
    }

    @GetMapping
    fun getAllFuelTypes(): List<FuelTypeEntity?> {
        return fuelTypeService.getAllFuelTypes()
    }
}