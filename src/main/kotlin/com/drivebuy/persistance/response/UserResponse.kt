package com.drivebuy.persistance.response

data class UserResponse(
    val id: String,
    val name: String,
    val email: String,
    val phone: String
) {
    companion object {
        fun fromEntity(user: com.drivebuy.persistance.entity.UserEntity): UserResponse {
            return UserResponse(
                id = user.firebaseId,
                name = user.name,
                email = user.email,
                phone = user.phone
            )
        }
    }
}