package com.drivebuy.service

import com.drivebuy.persistance.AdRepository
import com.drivebuy.persistance.entity.CarAdEntity
import org.springframework.stereotype.Service

@Service
class AdService(private val adRepository: AdRepository) {

    fun createAd(ad: CarAdEntity): CarAdEntity {
        return adRepository.save(ad)
    }

    fun getAdById(id: Long): CarAdEntity? {
        return adRepository.findById(id).orElse(null)
    }

    fun getAdsByUserId(userId: String): List<CarAdEntity> {
        return adRepository.findByUserId(userId)
    }
}