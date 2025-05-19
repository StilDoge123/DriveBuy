package com.drivebuy.service.car_info

import com.drivebuy.persistance.entity.car_info.CylinderCountEntity
import com.drivebuy.repository.car_info.CylinderCountRepository
import org.springframework.stereotype.Service


@Service
class CylinderCountService(private val cylinderCountRepository: CylinderCountRepository) {
    fun getCylinderCountById(id: Long): CylinderCountEntity? {
        return cylinderCountRepository.findById(id).orElse(null)
    }

    fun getCylinderCountByCount(cylinderCount: String): CylinderCountEntity? {
        return cylinderCountRepository.findByCylinderCount(cylinderCount)
    }

    fun getAllCylinderCounts(): List<CylinderCountEntity?> {
        return cylinderCountRepository.findAll()
    }
}