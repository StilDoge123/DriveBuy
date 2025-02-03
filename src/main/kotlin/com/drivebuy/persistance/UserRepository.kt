package com.drivebuy.persistance

import com.drivebuy.persistance.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, String> {
    fun findByFirebaseId(firebaseId: String): UserEntity?
    fun findByEmail(email: String): UserEntity?
}
