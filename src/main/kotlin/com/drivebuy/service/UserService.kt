package com.drivebuy.service

import com.drivebuy.exception.EmailAlreadyExistsException
import com.drivebuy.exception.InvalidEmailException
import com.drivebuy.exception.UserRegistrationException
import com.drivebuy.exception.WeakPasswordException
import com.drivebuy.persistance.dto.RegisterUserDTO
import com.drivebuy.persistance.entity.CarAdEntity
import com.drivebuy.repository.UserRepository
import com.drivebuy.persistance.entity.UserEntity
import com.drivebuy.persistance.request.UpdateUserRequest
import com.drivebuy.persistance.response.UserResponse
import com.drivebuy.repository.AdRepository
import com.google.firebase.auth.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val adRepository: AdRepository,
    ) {
    @Transactional
    fun getOrCreateUser(uid: String): UserEntity {
        return userRepository.findById(uid).orElseGet {
            val firebaseUser = FirebaseAuth.getInstance().getUser(uid)
            val newUser = UserEntity(
                firebaseId = firebaseUser.uid,
                email = firebaseUser.email,
                name = firebaseUser.displayName,
                phone = firebaseUser.phoneNumber
            )
            userRepository.save(newUser)
        }
    }

    fun registerUser(registerUserDTO: RegisterUserDTO): UserResponse {
        val firebaseUser = try {
            createFirebaseUser(
                registerUserDTO.email,
                registerUserDTO.password,
                registerUserDTO.email,
                registerUserDTO.phone
            )
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

    fun createFirebaseUser(email: String, password: String, name: String, phone: String): UserRecord {
        return try {
            val createRequest = UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setDisplayName(name)
                .setEmailVerified(false)
                .setDisabled(false)

            // Only set phone in Firebase if already E.164 compliant to avoid IllegalArgumentException
            if (phone.isNotBlank() && phone.matches(Regex("^\\+[1-9]\\d{1,14}$"))) {
                createRequest.setPhoneNumber(phone)
            }

            FirebaseAuth.getInstance().createUser(createRequest)
        } catch (e: FirebaseAuthException) {
            when (e.message) {
                "email-already-exists" -> throw EmailAlreadyExistsException("Email $email already registered")
                "invalid-email" -> throw InvalidEmailException("Invalid email format")
                "invalid-password" -> throw WeakPasswordException("Password must be at least 6 characters")
                else -> throw UserRegistrationException("Failed to create user: ${e.message}")
            }
        }
    }

    fun syncFirebaseUsersWithDatabase() {
        val firebaseUsers = getAllFirebaseUsers()
        val dbUserIds = userRepository.findAll().map { it.firebaseId }.toSet()

        for (firebaseUser in firebaseUsers) {
            if (firebaseUser.uid !in dbUserIds) {
                val newUser = UserEntity(
                    firebaseId = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    name = firebaseUser.displayName ?: "",
                    phone = firebaseUser.phoneNumber ?: ""
                )
                userRepository.save(newUser)
            }
        }
    }

    fun getAllFirebaseUsers(): List<UserRecord> {
        val users = mutableListOf<UserRecord>()
        var page: ListUsersPage = FirebaseAuth.getInstance().listUsers(null)
        while (true) {
            for (user in page.values) {
                users.add(user)
            }
            if (!page.hasNextPage()) break
            page = page.nextPage
        }
        return users
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

    fun saveAd(userId: String, adId: Long): List<CarAdEntity> {
        if (!userRepository.existsById(userId)) throw RuntimeException("User not found")
        if (!adRepository.existsById(adId)) throw RuntimeException("Ad not found")

        val user = userRepository.findByFirebaseId(userId)
        val ad = adRepository.findEntityById(adId)
        if (!user.savedAds.contains(ad)) {
            user.savedAds.add(ad)
            userRepository.save(user)
        }

        return user.savedAds
    }

    fun removeSavedAd(userId: String, adId: Long): List<CarAdEntity> {
        if (!userRepository.existsById(userId)) throw RuntimeException("User not found")
        if (!adRepository.existsById(adId)) throw RuntimeException("Ad not found")

        val user = userRepository.findByFirebaseId(userId)
        val ad = adRepository.findEntityById(adId)
        if (user.savedAds.contains(ad)) {
            user.savedAds.remove(ad)
            userRepository.save(user)
        }

        return user.savedAds
    }

    fun getSavedAds(userId: String): List<CarAdEntity> {
        if (!userRepository.existsById(userId)) throw RuntimeException("User not found")
        val user = userRepository.findByFirebaseId(userId)
        return user.savedAds
    }
}