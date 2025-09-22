package com.drivebuy.repository

import com.drivebuy.persistance.entity.CarAdEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

@Repository
interface AdRepository : JpaRepository<CarAdEntity, Long>, JpaSpecificationExecutor<CarAdEntity> {
    fun findByUserId(userId: String): List<CarAdEntity>
    fun findEntityById(id: Long): CarAdEntity

    @Query("SELECT COUNT(a) FROM CarAdEntity a JOIN a.imageUrls iu WHERE iu = :url AND a.id <> :adId")
    fun countOtherAdsReferencingImage(@Param("url") url: String, @Param("adId") adId: Long): Long
}