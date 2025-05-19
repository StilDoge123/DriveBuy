package com.drivebuy.service.car_info

import com.drivebuy.persistance.entity.car_info.TransmissionTypeEntity
import com.drivebuy.repository.car_info.TransmissionTypeRepository
import org.springframework.stereotype.Service


@Service
class TransmissionTypeService(private val transmissionTypeRepository: TransmissionTypeRepository) {
    fun getTransmissionTypeById(id: Long): TransmissionTypeEntity? {
        return transmissionTypeRepository.findById(id).orElse(null)
    }

    fun getTransmissionTypeByTypeName(transmissionTypeName: String): TransmissionTypeEntity? {
        return transmissionTypeRepository.findByTransmissionTypeName(transmissionTypeName)
    }

    fun getAllTransmissionTypes(): List<TransmissionTypeEntity?> {
        return transmissionTypeRepository.findAll()
    }
}