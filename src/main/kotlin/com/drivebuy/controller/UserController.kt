package com.drivebuy.controller

import com.drivebuy.persistance.dto.RegisterUserDTO
import com.drivebuy.persistance.entity.CarAdEntity
import com.drivebuy.persistance.entity.UserEntity
import com.drivebuy.persistance.request.UpdateAdRequest
import com.drivebuy.persistance.request.UpdateUserRequest
import com.drivebuy.persistance.response.UserResponse
import com.drivebuy.service.UserService
import com.drivebuy.util.AuthUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody registerDto: RegisterUserDTO): UserResponse {
        return userService.registerUser(registerDto)
    }

    @PostMapping("/login")
    fun getOrCreateUser(request: HttpServletRequest): ResponseEntity<UserResponse> {
        val uid = AuthUtil.getUidFromRequest(request)
        val user = userService.getOrCreateUser(uid)
        return ResponseEntity.ok(UserResponse.fromEntity(user))
    }

    @GetMapping("/{firebaseId}")
    fun getUser(@PathVariable firebaseId: String): UserEntity? {
        return userService.getUserById(firebaseId)
    }

    @GetMapping
    fun getAllUsers(): List<UserEntity> {
        return userService.getAllUsers()
    }

    @GetMapping("/me")
    fun getCurrentUser(request: HttpServletRequest): UserResponse {
        val uid = AuthUtil.getUidFromRequest(request)
        val user = userService.getUserById(uid)
        return UserResponse.fromEntity(user)
    }

    @PatchMapping("/{userId}")
    fun updateUser(
        @PathVariable userId: String,
        @RequestBody updateUserRequest: UpdateUserRequest,
        request: HttpServletRequest
    ): UserResponse {
        val uid = AuthUtil.getUidFromRequest(request)
        if (uid != userId) throw RuntimeException("Unauthorized")
        val updatedUser = userService.updateUser(userId, updateUserRequest)
        return UserResponse.fromEntity(updatedUser)
    }

    @PostMapping("/{userId}/saved-ads/{adId}")
    fun saveAd(
        @PathVariable userId: String,
        @PathVariable adId: Long,
        request: HttpServletRequest
    ) : List<CarAdEntity> {
        val uid = AuthUtil.getUidFromRequest(request)
        if (uid != userId) throw RuntimeException("Unauthorized")

        return userService.saveAd(userId, adId)
    }

    @PostMapping("/{userId}/saved-ads/remove/{adId}")
    fun removeSavedAd(
        @PathVariable userId: String,
        @PathVariable adId: Long,
        request: HttpServletRequest
    ) : List<CarAdEntity> {
        val uid = AuthUtil.getUidFromRequest(request)
        if (uid != userId) throw RuntimeException("Unauthorized")

        return userService.removeSavedAd(userId, adId)
    }

    @GetMapping("/{userId}/saved-ads")
    fun getSavedAds(
        @PathVariable userId: String,
        request: HttpServletRequest
    ): List<CarAdEntity> {
        val uid = AuthUtil.getUidFromRequest(request)
        if (uid != userId) throw RuntimeException("Unauthorized")

        return userService.getSavedAds(userId)
    }

    @PostMapping("/sync-firebase-users")
    fun syncFirebaseUsers(): ResponseEntity<String> {
        userService.syncFirebaseUsersWithDatabase()
        return ResponseEntity.ok("Firebase users synced to database.")
    }

}