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
    @ManyToMany
    @JoinTable(
    name = "user_saved_ads",
    joinColumns = [JoinColumn(name = "user_id")],
    inverseJoinColumns = [JoinColumn(name = "ad_id")])
    val savedAds: MutableList<CarAdEntity> = mutableListOf()
)