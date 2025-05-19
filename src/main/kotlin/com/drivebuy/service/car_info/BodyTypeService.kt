package com.drivebuy.service.car_info

import com.drivebuy.persistance.entity.car_info.BodyTypeEntity
import com.drivebuy.repository.car_info.BodyTypeRepository
import org.springframework.stereotype.Service

@Service
class BodyTypeService(private val bodyTypeRepository: BodyTypeRepository) {
    fun getBodyTypeById(id: Long): BodyTypeEntity? {
        return bodyTypeRepository.findById(id).orElse(null)
    }

    fun getBodyTypeByBodyTypeName(bodyTypeName: String): BodyTypeEntity? {
        return bodyTypeRepository.findByBodyTypeName(bodyTypeName)
    }

    fun getAllBodyTypes(): List<BodyTypeEntity?> {
        return bodyTypeRepository.findAll()
    }
}