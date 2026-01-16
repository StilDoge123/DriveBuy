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

    fun updateDoorCount(id: Long, count: String): DoorCountEntity {
        val doorCount = doorCountRepository.findById(id)
            .orElseThrow { NoSuchElementException("Door count not found") }

        doorCount.doorCount = count
        return doorCountRepository.save(doorCount)
    }

    fun deleteDoorCount(id: Long) {
        val doorCount = doorCountRepository.findById(id)
            .orElseThrow { NoSuchElementException("Door count not found") }

        doorCountRepository.delete(doorCount)
    }
}