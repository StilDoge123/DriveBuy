package com.drivebuy.controller

import com.drivebuy.persistance.entity.UserEntity
import com.drivebuy.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody user: UserEntity): UserEntity {
        return userService.createUser(user)
    }

    @GetMapping("/{firebaseId}")
    fun getUser(@PathVariable firebaseId: String): UserEntity? {
        return userService.getUserById(firebaseId)
    }

    @GetMapping
    fun getAllUsers(): List<UserEntity> {
        return userService.getAllUsers()
    }
}