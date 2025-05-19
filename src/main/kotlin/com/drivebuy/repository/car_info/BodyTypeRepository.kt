package com.drivebuy.repository.car_info

import com.drivebuy.persistance.entity.car_info.BodyTypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BodyTypeRepository : JpaRepository<BodyTypeEntity, Long> {
    fun findByBodyTypeName(bodyTypeName: String): BodyTypeEntity?
}