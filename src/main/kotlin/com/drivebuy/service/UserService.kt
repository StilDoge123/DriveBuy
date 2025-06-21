package com.drivebuy.service

import com.drivebuy.exception.EmailAlreadyExistsException
import com.drivebuy.exception.InvalidEmailException
import com.drivebuy.exception.UserRegistrationException
import com.drivebuy.exception.WeakPasswordException
import com.drivebuy.persistance.dto.RegisterUserDTO
import com.drivebuy.persistance.entity.CarAdEntity
import com.drivebuy.persistance.entity.SavedAdEntity
import com.drivebuy.repository.UserRepository
import com.drivebuy.persistance.entity.UserEntity
import com.drivebuy.persistance.request.UpdateUserRequest
import com.drivebuy.persistance.response.UserResponse
import com.drivebuy.repository.AdRepository
import com.drivebuy.repository.SavedAdRepository
import com.google.firebase.auth.*
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val adRepository: AdRepository,
    private val savedAdRepository: SavedAdRepository,
    ) {
    fun createUser(registerDto: RegisterUserDTO): UserEntity {
        val firebaseUser = createFirebaseUser(registerDto.email, registerDto.password)

        return userRepository.save(
            UserEntity(
                firebaseId = firebaseUser.uid,
                name = registerDto.name,
                email = registerDto.email,
                phone = registerDto.phone,
                savedAds = mutableListOf()
            )
        )
    }

    fun registerUser(registerUserDTO: RegisterUserDTO): UserResponse {
        val firebaseUser = try {
            createFirebaseUser(registerUserDTO.email, registerUserDTO.password)
        } catch (e: FirebaseAuthException) {
            when (e.message) {
                "email-already-exists" -> throw EmailAlreadyExistsException("Email ${registerUserDTO.email} already registered")
                "invalid-email" -> throw InvalidEmailException("Invalid email format")
                "invalid-password" -> throw WeakPasswordException("Password must be at least 6 characters")
                else -> throw UserRegistrationException("Failed to create user: ${e.message}")
            }
        }

        val userEntity = UserEntity(
            firebaseId = firebaseUser.uid,
            name = registerUserDTO.name,
            email = registerUserDTO.email,
            phone = registerUserDTO.phone,
            savedAds = mutableListOf()
        )

        val savedUser = userRepository.save(userEntity)

        return UserResponse(
            id = savedUser.firebaseId,
            name = savedUser.name,
            email = savedUser.email,
            phone = savedUser.phone
        )
    }

    fun createFirebaseUser(email: String, password: String): UserRecord {
        return try {
            FirebaseAuth.getInstance().createUser(
                UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password)
                    .setEmailVerified(false)
                    .setDisabled(false)
            )
        } catch (e: FirebaseAuthException) {
            when (e.message) {
                "email-already-exists" -> throw EmailAlreadyExistsException("Email $email already registered")
                "invalid-email" -> throw InvalidEmailException("Invalid email format")
                "invalid-password" -> throw WeakPasswordException("Password must be at least 6 characters")
                else -> throw UserRegistrationException("Failed to create user: ${e.message}")
            }
        }
    }

    fun getUserById(firebaseId: String): UserEntity {
        return userRepository.findById(firebaseId).orElse(null)
    }

    fun getAllUsers(): List<UserEntity> {
        return userRepository.findAll()
    }

    fun updateUser(userId: String, updateUserRequest: UpdateUserRequest): UserEntity {
        val user = userRepository.findById(userId)
            .orElseThrow { RuntimeException("User not found") }

        updateUserRequest.name?.let { user.name = it }
        updateUserRequest.email?.let { user.email = it }
        updateUserRequest.phone?.let { user.phone = it }

        return userRepository.save(user)
    }

    fun saveAd(userId: String, adId: Long) {
        if (!userRepository.existsById(userId)) throw RuntimeException("User not found")
        if (!adRepository.existsById(adId)) throw RuntimeException("Ad not found")
        if (!savedAdRepository.existsByUserIdAndAdId(userId, adId)) {
            savedAdRepository.save(SavedAdEntity(userId = userId, adId = adId))
        }
    }

    fun removeSavedAd(userId: String, adId: Long) {
        if (!userRepository.existsById(userId)) throw RuntimeException("User not found")
        if (!adRepository.existsById(adId)) throw RuntimeException("Ad not found")
        if (savedAdRepository.existsByUserIdAndAdId(userId, adId)) {
            savedAdRepository.delete(SavedAdEntity(userId = userId, adId = adId))
        }
    }

    fun getSavedAds(userId: String): List<CarAdEntity> {
        if (!userRepository.existsById(userId)) throw RuntimeException("User not found")
        val savedAdIds = savedAdRepository.findByUserId(userId).map { it.adId }
        return adRepository.findAllById(savedAdIds)
    }
}