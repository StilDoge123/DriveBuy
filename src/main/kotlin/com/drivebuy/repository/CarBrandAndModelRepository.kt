package com.drivebuy.repository

import com.drivebuy.persistance.entity.CarModel
import com.drivebuy.persistance.enums.CarBrand
import org.springframework.data.jpa.repository.JpaRepository

interface CarBrandRepository : JpaRepository<CarBrand, Long>

interface CarModelRepository : JpaRepository<CarModel, Long>