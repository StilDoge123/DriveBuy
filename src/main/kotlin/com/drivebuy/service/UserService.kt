package com.drivebuy.service

import com.drivebuy.repository.UserRepository
import com.drivebuy.persistance.entity.UserEntity
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(user: UserEntity): UserEntity {
        return userRepository.save(user)
    }

    fun getUserById(firebaseId: String): UserEntity? {
        return userRepository.findById(firebaseId).orElse(null)
    }

    fun getAllUsers(): List<UserEntity> {
        return userRepository.findAll()
    }
}