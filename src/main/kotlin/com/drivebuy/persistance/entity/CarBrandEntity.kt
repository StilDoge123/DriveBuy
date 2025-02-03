package com.drivebuy.persistance.entity

import jakarta.persistence.*

@Entity
@Table(name = "car_brand")
data class CarBrand(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val name: String
)
