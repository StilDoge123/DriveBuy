package com.drivebuy.repository

import com.drivebuy.persistance.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

@Repository
interface UserRepository : JpaRepository<UserEntity, String> {
    fun findByFirebaseId(firebaseId: String): UserEntity
    fun findByEmail(email: String): UserEntity?
    
    @Modifying
    @Query(value = "DELETE FROM user_saved_ads WHERE ad_id = :adId", nativeQuery = true)
    fun deleteSavedAdLinksByAdId(@Param("adId") adId: Long)
}
