package com.drivebuy.repository.car_info

import com.drivebuy.persistance.entity.car_info.CarConditionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CarConditionRepository : JpaRepository<CarConditionEntity, Long> {
    fun findByConditionName(conditionName: String): CarConditionEntity?
}