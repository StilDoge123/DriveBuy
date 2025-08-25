package com.drivebuy.persistance.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    val firebaseId: String,
    var name: String,
    var email: String,
    var phone: String,
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    name = "user_saved_ads",
    joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "firebaseId")],
    inverseJoinColumns = [JoinColumn(name = "ad_id")])
    val savedAds: MutableList<CarAdEntity> = mutableListOf()
)