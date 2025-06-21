package com.drivebuy.persistance.dto

data class RegisterUserDTO(
    val name: String,
    val email: String,
    val password: String,
    val phone: String,
)