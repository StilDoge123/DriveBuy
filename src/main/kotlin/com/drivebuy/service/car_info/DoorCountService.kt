package com.drivebuy.service.car_info

import com.drivebuy.persistance.entity.car_info.DoorCountEntity
import com.drivebuy.repository.car_info.DoorCountRepository
import org.springframework.stereotype.Service


@Service
class DoorCountService(private val doorCountRepository: DoorCountRepository) {
    fun getDoorCountById(id: Long): DoorCountEntity? {
        return doorCountRepository.findById(id).orElse(null)
    }

    fun getDoorCountByCount(doorCount: String): DoorCountEntity? {
        return doorCountRepository.findByDoorCount(doorCount)
    }

    fun getAllDoorCounts(): List<DoorCountEntity?> {
        return doorCountRepository.findAll()
    }
}