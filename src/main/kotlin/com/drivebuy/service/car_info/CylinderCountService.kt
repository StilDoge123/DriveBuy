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

    fun updateCylinderCount(id: Long, count: String): CylinderCountEntity {
        val cylinderCount = cylinderCountRepository.findById(id)
            .orElseThrow { NoSuchElementException("Cylinder count not found") }

        cylinderCount.cylinderCount = count
        return cylinderCountRepository.save(cylinderCount)
    }

    fun deleteCylinderCount(id: Long) {
        val cylinderCount = cylinderCountRepository.findById(id)
            .orElseThrow { NoSuchElementException("Cylinder count not found") }

        cylinderCountRepository.delete(cylinderCount)
    }
}