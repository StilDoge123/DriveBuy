package com.drivebuy.persistance.request

data class UpdateUserRequest(
    val name: String?,
    val email: String?,
    val phone: String?
)