package com.drivebuy.persistance.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    val firebaseId: String,
    val name: String,
    val email: String,
    val phone: String,

    @ElementCollection
    @CollectionTable(name = "user_ads", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "ad_id")
    val adIds: List<Long> = mutableListOf()
)