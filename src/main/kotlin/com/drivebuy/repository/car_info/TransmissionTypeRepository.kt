package com.drivebuy.repository.car_info

import com.drivebuy.persistance.entity.car_info.TransmissionTypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransmissionTypeRepository : JpaRepository<TransmissionTypeEntity, Long> {
    fun findByTransmissionTypeName(transmissionTypeName: String): TransmissionTypeEntity?
}