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

    fun updateTransmissionType(id: Long, name: String): TransmissionTypeEntity {
        val transmissionType = transmissionTypeRepository.findById(id)
            .orElseThrow { NoSuchElementException("Transmission type not found") }

        transmissionType.transmissionTypeName = name
        return transmissionTypeRepository.save(transmissionType)
    }

    fun deleteTransmissionType(id: Long) {
        val transmissionType = transmissionTypeRepository.findById(id)
            .orElseThrow { NoSuchElementException("Transmission type not found") }

        transmissionTypeRepository.delete(transmissionType)
    }
}