package com.drivebuy.service.car_info

import com.drivebuy.persistance.entity.car_info.SteeringPositionEntity
import com.drivebuy.repository.car_info.SteeringPositionRepository
import org.springframework.stereotype.Service


@Service
class SteeringPositionService(private val steeringPositionRepository: SteeringPositionRepository) {
    fun getSteeringPositionById(id: Long): SteeringPositionEntity? {
        return steeringPositionRepository.findById(id).orElse(null)
    }

    fun getSteeringPositionByPositionName(steeringPositionName: String): SteeringPositionEntity? {
        return steeringPositionRepository.findBySteeringPositionName(steeringPositionName)
    }

    fun getAllSteeringPositions(): List<SteeringPositionEntity?> {
        return steeringPositionRepository.findAll()
    }

    fun updateSteeringPosition(id: Long, name: String): SteeringPositionEntity {
        val steeringPosition = steeringPositionRepository.findById(id)
            .orElseThrow { NoSuchElementException("Steering position not found") }

        steeringPosition.steeringPositionName = name
        return steeringPositionRepository.save(steeringPosition)
    }

    fun deleteSteeringPosition(id: Long) {
        val steeringPosition = steeringPositionRepository.findById(id)
            .orElseThrow { NoSuchElementException("Steering position not found") }

        steeringPositionRepository.delete(steeringPosition)
    }
}